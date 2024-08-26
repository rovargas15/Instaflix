package com.instaleap.data.remote.datasource

import com.instaleap.data.clientMockkError
import com.instaleap.data.clienteMockk
import com.instaleap.data.remote.response.BaseResponse
import com.instaleap.data.remote.response.MovieDetailResponse
import com.instaleap.data.remote.response.MovieResponse
import com.instaleap.data.responseMockk
import com.instaleap.data.util.Constant
import io.ktor.client.engine.mock.MockEngine
import io.ktor.http.fullPath
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import kotlin.test.Test

class MovieDataSourceTest {
    private lateinit var movieDataSource: MovieDataSource

    private val jsonResponseList =
        "{\"page\":1,\"results\":[],\"total_pages\":10,\"total_results\":100}"
    private val jsonResponseById =
        "{\"adult\":false,\"backdrop_path\":\"/path/to/backdrop.jpg\",\"belongs_to_collection\":{\"backdrop_path\":\"/path/to/collection_backdrop.jpg\",\"id\":123,\"name\":\"Collection Name\",\"poster_path\":\"/path/to/collection_poster.jpg\"},\"budget\":150000000,\"genres\":[{\"id\":28,\"name\":\"Action\"},{\"id\":12,\"name\":\"Adventure\"}],\"homepage\":\"https://www.example.com\",\"id\":456,\"imdb_id\":\"tt1234567\",\"original_language\":\"en\",\"original_title\":\"Original Movie Title\",\"overview\":\"This is a brief overview of the movie.\",\"popularity\":8.5,\"poster_path\":\"/path/to/poster.jpg\",\"production_companies\":[{\"id\":1,\"logo_path\":\"/path/to/logo.png\",\"name\":\"Production Company Name\",\"origin_country\":\"US\"}],\"production_countries\":[{\"iso_3166_1\":\"US\",\"name\":\"United States\"}],\"release_date\":\"2024-08-25\",\"revenue\":500000000,\"runtime\":120,\"spoken_languages\":[{\"english_name\":\"English\",\"iso_639_1\":\"en\",\"name\":\"English\"}],\"status\":\"Released\",\"tagline\":\"An exciting tagline\",\"title\":\"Movie Title\",\"video\":false,\"vote_average\":7.8,\"vote_count\":2000}"

    private val clientSuccess =
        clienteMockk(
            MockEngine.invoke {
                when (it.url.fullPath) {
                    Constant.Api.MOVIE_BY_QUERY.format("test"),
                    Constant.Api.MOVIE_BY_CATEGORY.format(
                        "cat",
                        1,
                    ),
                    -> {
                        responseMockk(
                            content = jsonResponseList,
                        )
                    }

                    Constant.Api.MOVIE_BY_ID.format(1) -> {
                        responseMockk(
                            content = jsonResponseById,
                        )
                    }

                    else -> error("Unhandled ${it.url.encodedPath}")
                }
            },
        )

    @Test
    fun `Give success When getMovies Then return MovieResponse`() {
        movieDataSource = MovieDataSource(clientSuccess)

        runBlocking {
            val expectedResponse =
                Json.decodeFromString<BaseResponse<MovieResponse>>(jsonResponseList)

            val actualResponse: BaseResponse<MovieResponse> = movieDataSource.getMovies("cat", 1)
            assertEquals(expectedResponse, actualResponse)
        }
    }

    @Test
    fun `Give error When getMovies Then return throw Throwable`() {
        movieDataSource = MovieDataSource(clientMockkError())

        assertThrows(Throwable::class.java) {
            runBlocking {
                movieDataSource.getMovies("cat", 1)
            }
        }
    }

    @Test
    fun `Give success When getMoviesByQuery Then return MovieResponse`() {
        movieDataSource = MovieDataSource(clientSuccess)

        runBlocking {
            val expectedResponse =
                Json.decodeFromString<BaseResponse<MovieResponse>>(jsonResponseList)

            val actualResponse: BaseResponse<MovieResponse> =
                movieDataSource.getMoviesByQuery("test")
            assertEquals(expectedResponse, actualResponse)
        }
    }

    @Test
    fun `Give error When getMoviesByQuery Then return throw Throwable`() {
        movieDataSource = MovieDataSource(clientMockkError())

        assertThrows(Throwable::class.java) {
            runBlocking {
                movieDataSource.getMoviesByQuery("test")
            }
        }
    }

    @Test
    fun `Give success When getMovieById Then return MovieDetailResponse`() {
        movieDataSource = MovieDataSource(clientSuccess)

        runBlocking {
            val expectedResponse = Json.decodeFromString<MovieDetailResponse>(jsonResponseById)

            val actualResponse: MovieDetailResponse = movieDataSource.getMovieById(1)
            assertEquals(expectedResponse, actualResponse)
        }
    }

    @Test
    fun `Give error When getMovieById Then return throw Throwable`() {
        movieDataSource = MovieDataSource(clientMockkError())

        assertThrows(Throwable::class.java) {
            runBlocking {
                movieDataSource.getMovieById(1)
            }
        }
    }
}
