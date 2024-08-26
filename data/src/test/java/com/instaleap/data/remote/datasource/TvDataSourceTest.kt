package com.instaleap.data.remote.datasource

import com.instaleap.data.clientMockkError
import com.instaleap.data.clienteMockk
import com.instaleap.data.remote.response.BaseResponse
import com.instaleap.data.remote.response.TvDetailResponse
import com.instaleap.data.remote.response.TvResponse
import com.instaleap.data.responseMockk
import com.instaleap.data.util.Constant
import io.ktor.client.engine.mock.MockEngine
import io.ktor.http.fullPath
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import kotlin.test.Test

class TvDataSourceTest {
    private lateinit var movieDataSource: TvDataSource

    private val jsonResponseList =
        "{\"page\":1,\"results\":[],\"total_pages\":10,\"total_results\":100}"
    private val jsonResponseById =
        "{\"adult\":false,\"backdrop_path\":\"/path/to/backdrop.jpg\",\"created_by\":[{\"credit_id\":\"12345\",\"gender\":2,\"id\":1,\"name\":\"Jane Doe\",\"original_name\":\"Jane Doe Original\"}],\"episode_run_time\":[60,45],\"first_air_date\":\"2023-01-01\",\"genres\":[{\"id\":18,\"name\":\"Drama\"},{\"id\":10765,\"name\":\"Sci-Fi\"}],\"homepage\":\"https://www.example-show.com\",\"id\":678,\"in_production\":true,\"languages\":[\"en\",\"fr\"],\"last_air_date\":\"2024-08-25\",\"last_episode_to_air\":{\"air_date\":\"2024-08-25\",\"episode_number\":10,\"episode_type\":\"Finale\",\"id\":999,\"name\":\"Season Finale\",\"overview\":\"The thrilling season finale of the show.\",\"production_code\":\"FNL2024\",\"runtime\":50,\"season_number\":1,\"show_id\":678,\"still_path\":\"/path/to/last_episode_still.jpg\",\"vote_average\":8.9,\"vote_count\":300},\"name\":\"Example TV Show\",\"networks\":[{\"id\":1,\"logo_path\":\"/path/to/network_logo.png\",\"name\":\"Example Network\",\"origin_country\":\"US\"}],\"next_episode_to_air\":{\"air_date\":\"2024-09-01\",\"episode_number\":1,\"episode_type\":\"Premiere\",\"id\":1000,\"name\":\"Season Premiere\",\"overview\":\"The exciting premiere of the new season.\",\"production_code\":\"PRE2024\",\"runtime\":55,\"season_number\":2,\"show_id\":678,\"still_path\":\"/path/to/next_episode_still.jpg\",\"vote_average\":0,\"vote_count\":0},\"number_of_episodes\":20,\"number_of_seasons\":2,\"origin_country\":[\"US\"],\"original_language\":\"en\",\"original_name\":\"Example TV Show Original\",\"overview\":\"An overview of the Example TV Show.\",\"popularity\":9.2,\"poster_path\":\"/path/to/poster.jpg\",\"production_companies\":[{\"id\":1,\"logo_path\":\"/path/to/company_logo.png\",\"name\":\"Production Company\",\"origin_country\":\"US\"}],\"production_countries\":[{\"iso_3166_1\":\"US\",\"name\":\"United States\"}],\"seasons\":[{\"air_date\":\"2023-01-01\",\"episode_count\":10,\"id\":1,\"name\":\"Season 1\",\"overview\":\"Overview of the first season.\",\"poster_path\":\"/path/to/season1_poster.jpg\",\"season_number\":1,\"vote_average\":8.0},{\"air_date\":\"2024-01-01\",\"episode_count\":10,\"id\":2,\"name\":\"Season 2\",\"overview\":\"Overview of the second season.\",\"poster_path\":\"/path/to/season2_poster.jpg\",\"season_number\":2,\"vote_average\":8.5}],\"spoken_languages\":[{\"english_name\":\"English\",\"iso_639_1\":\"en\",\"name\":\"English\"},{\"english_name\":\"French\",\"iso_639_1\":\"fr\",\"name\":\"Français\"}],\"status\":\"Returning Series\",\"tagline\":\"A thrilling tagline for the show\",\"type\":\"Scripted\",\"vote_average\":8.5,\"vote_count\":2500}"

    private val clientSuccess =
        clienteMockk(
            MockEngine.invoke {
                when (it.url.fullPath) {
                    "/tv/1?language=es" -> {
                        responseMockk(
                            content = jsonResponseById,
                        )
                    }

                    Constant.Api.TV_BY_QUERY.format("test"),
                    Constant.Api.TV_BY_CATEGORY.format("cat", 1),
                    -> {
                        responseMockk(
                            content = jsonResponseList,
                        )
                    }

                    else -> error("Unhandled ${it.url.encodedPath}")
                }
            },
        )

    @Test
    fun `Give success When getTvByCategory Then return TvResponse`() {
        movieDataSource = TvDataSource(clientSuccess)

        runBlocking {
            val expectedResponse =
                Json.decodeFromString<BaseResponse<TvResponse>>(jsonResponseList)

            val actualResponse: BaseResponse<TvResponse> = movieDataSource.getTvByCategory("cat", 1)
            assertEquals(expectedResponse, actualResponse)
        }
    }

    @Test
    fun `Give error When getTvByCategory Then return throw Throwable`() {
        movieDataSource = TvDataSource(clientMockkError())

        assertThrows(Throwable::class.java) {
            runBlocking {
                movieDataSource.getTvByCategory("cat", 1)
            }
        }
    }

    @Test
    fun `Give success When getTvByQuery Then return TvResponse`() {
        movieDataSource = TvDataSource(clientSuccess)

        runBlocking {
            val expectedResponse =
                Json.decodeFromString<BaseResponse<TvResponse>>(jsonResponseList)

            val actualResponse: BaseResponse<TvResponse> =
                movieDataSource.getTvByQuery("test")
            assertEquals(expectedResponse, actualResponse)
        }
    }

    @Test
    fun `Give error When getTvByQuery Then return throw Throwable`() {
        movieDataSource = TvDataSource(clientMockkError())

        assertThrows(Throwable::class.java) {
            runBlocking {
                movieDataSource.getTvByQuery("test")
            }
        }
    }

    @Test
    fun `Give success When getTvById Then return TvDetailResponse`() {
        movieDataSource = TvDataSource(clientSuccess)

        runBlocking {
            val expectedResponse = Json.decodeFromString<TvDetailResponse>(jsonResponseById)

            val actualResponse: TvDetailResponse = movieDataSource.getTvById(1)
            assertEquals(expectedResponse, actualResponse)
        }
    }

    @Test
    fun `Give error When getTvById Then return throw Throwable`() {
        movieDataSource = TvDataSource(clientMockkError())

        assertThrows(Throwable::class.java) {
            runBlocking {
                movieDataSource.getTvById(1)
            }
        }
    }
}
