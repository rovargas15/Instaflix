package com.instaleap.data.repository

import com.instaleap.data.local.database.InstaflixDatabase
import com.instaleap.data.local.entity.MovieEntity
import com.instaleap.data.remote.datasource.MovieDataSource
import com.instaleap.data.remote.response.BaseResponse
import com.instaleap.data.remote.response.MovieDetailResponse
import com.instaleap.data.remote.response.MovieResponse
import com.instaleap.domain.exception.DomainException
import com.instaleap.domain.model.Movie
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MovieRepositoryImplTest {
    @MockK
    lateinit var dataSourceRemote: MovieDataSource

    @MockK(relaxed = true)
    lateinit var db: InstaflixDatabase

    @InjectMockKs
    lateinit var movieRepositoryImpl: MovieRepositoryImpl

    private lateinit var movieEntity: MovieEntity

    private lateinit var movieResponse: MovieResponse

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        movieEntity =
            MovieEntity(
                adult = false,
                backdropPath = "/path/to/backdrop.jpg",
                genreIds = listOf(28, 12, 16),
                movieId = 1,
                originalLanguage = "en",
                originalTitle = "Example Movie Title",
                overview = "This is an overview of the movie. It provides a brief description of the plot and key details.",
                popularity = 7.8,
                posterPath = "/path/to/poster.jpg",
                releaseDate = "2024-08-25",
                title = "Example Movie",
                video = false,
                voteAverage = 8.1,
                voteCount = 300,
                isFavorite = 1,
                category = "cat",
            )

        movieResponse =
            MovieResponse(
                adult = false,
                backdropPath = "/path/to/backdrop.jpg",
                genreIds = listOf(28, 12, 16), // Ejemplo de géneros: Acción, Aventura, Animación
                id = 456,
                originalLanguage = "en",
                originalTitle = "Example Movie Title",
                overview = "This is an overview of the movie. It provides a brief description of the plot and key details.",
                popularity = 7.8,
                posterPath = "/path/to/poster.jpg",
                releaseDate = "2024-08-25",
                title = "Example Movie",
                video = false,
                voteAverage = 8.1,
                voteCount = 300,
            )
    }

    @Test
    fun `Give success When getMovies Then return result Movie`() {
        val response =
            BaseResponse(
                page = 1,
                results = listOf(movieResponse),
                totalPages = 1,
                totalResults = 1,
            )

        coEvery { dataSourceRemote.getMovies("cat", 1) } returns response

        runBlocking {
            movieRepositoryImpl.getMovies("cat",1 ).apply {
                assert(isSuccess)
            }
        }

        validateSaveMovie(category = "cat", response = response)

        coVerify { dataSourceRemote.getMovies("cat", 1) }
    }

    @Test
    fun `Give error When getMovies Then return result failed`() {
        coEvery { dataSourceRemote.getMovies("cat", 1) } throws Exception("Error")

        runBlocking {
            val result = movieRepositoryImpl.getMovies("cat", 1)
            assert(result.isFailure)
            assert(result.exceptionOrNull() is DomainException)
        }

        coVerify { dataSourceRemote.getMovies("cat", 1) }
    }

    @Test
    fun `Give movie When getMovieById Then return result Movie`() {
        coEvery { db.movieDao().getMovieById(1) } returns flowOf(movieEntity)
        runBlocking {
            val result = movieRepositoryImpl.getMovieById(1)
            Assert.assertEquals(result.first(), movieEntity.toDomain())
        }
        coVerify { db.movieDao().getMovieById(1) }
    }

    @Test
    fun `Give success When getMovieDetailById Then return result Movie`() {
        val movie = mockk<MovieDetailResponse>()
        coEvery { dataSourceRemote.getMovieById(1) } returns movie
        every { movie.toDomain() } returns mockk()

        runBlocking {
            val result = movieRepositoryImpl.getMovieDetailById(1)
            assert(result.isSuccess)
        }

        coVerify { dataSourceRemote.getMovieById(1) }
        verify { movie.toDomain() }
    }

    @Test
    fun `Give success When getSearchMovie Then return result Movie`() {
        val response =
            BaseResponse(
                page = 1,
                results = listOf(movieResponse),
                totalPages = 1,
                totalResults = 1,
            )

        coEvery { dataSourceRemote.getMoviesByQuery("query") } returns response

        runBlocking {
            movieRepositoryImpl.getSearchMovie("query").apply {
                assert(isSuccess)
            }
        }
        validateSaveMovie(category = "query", response = response)

        coVerify { dataSourceRemote.getMoviesByQuery("query") }
    }

    @Test
    fun `Give error When getSearchMovie Then return result failed`() {
        coEvery { dataSourceRemote.getMoviesByQuery("query") } throws Exception("Error")

        runBlocking {
            movieRepositoryImpl.getSearchMovie("query").apply {
                assert(isFailure)
                assert(exceptionOrNull() is DomainException)
            }
        }
        coVerify { dataSourceRemote.getMoviesByQuery("query") }
    }

    @Test
    fun `Give success When getFavoriteMovies Then return result Movie`() {
        val movieEntity = mockk<MovieEntity>()
        coEvery { db.movieDao().getAllAsFlow() } returns flowOf(listOf(movieEntity))
        every { movieEntity.toDomain() } returns mockk<Movie>()

        runBlocking {
            movieRepositoryImpl.getFavoriteMovie().firstOrNull()?.let {
                assert(it is List<Movie>)
                assert(it.isNotEmpty())
            }
        }

        verify {
            db.movieDao().getAllAsFlow()
            movieEntity.toDomain()
        }
    }

    @Test
    fun `Give success When updateMovie Then return nothing`() {
        val movie =
            mockk<Movie> {
                every { id } returns 1
                every { isFavorite } returns true
            }
        coEvery { db.movieDao().updateMovie(true, 1) } returns 1

        runBlocking {
            movieRepositoryImpl.updateMovie(movie)
        }

        coVerify {
            db.movieDao().updateMovie(true, 1)
        }
    }

    @Test
    fun `Give success When getAllCache Then return nothing`() {
        val movieEntity = mockk<MovieEntity>()
        coEvery { db.movieDao().getAll() } returns flowOf(listOf(movieEntity))
        every { movieEntity.toDomain() } returns mockk<Movie>()

        runBlocking {
            movieRepositoryImpl.getAllCache().firstOrNull()?.let {
                assert(it is List<Movie>)
                assert(it.isNotEmpty())
            }
        }

        verify {
            db.movieDao().getAll()
        }
    }

    private fun validateSaveMovie(
        response: BaseResponse<MovieResponse>,
        category: String,
    ) {
        val movieEntities = response.results.map { it.toEntity(category) }

        coEvery {
            db.movieDao().insertAll(movieEntities)
        } just runs

        runBlocking {
            val results = movieRepositoryImpl.saveMovies(response, category)

            assert(results.results == response.results.map { it.toDomain(category) })
            assert(results.totalResults == response.totalResults)
            assert(results.page == response.page)
            assert(results.totalPages == response.totalPages)
        }

        coVerify { db.movieDao().insertAll(movieEntities) }
    }
}
