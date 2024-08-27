package com.instaleap.data.repository

import com.instaleap.data.local.database.InstaflixDatabase
import com.instaleap.data.local.entity.TvEntity
import com.instaleap.data.remote.datasource.TvDataSource
import com.instaleap.data.remote.response.BaseResponse
import com.instaleap.data.remote.response.TvDetailResponse
import com.instaleap.data.remote.response.TvResponse
import com.instaleap.domain.exception.DomainException
import com.instaleap.domain.model.Tv
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

class TvRepositoryImplTest {
    @MockK
    lateinit var dataSourceRemote: TvDataSource

    @MockK(relaxed = true)
    lateinit var db: InstaflixDatabase

    @InjectMockKs
    lateinit var tvRepositoryImpl: TvRepositoryImpl

    private lateinit var tvEntity: TvEntity

    private lateinit var tvResponse: TvResponse

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        tvEntity =
            TvEntity(
                id = 789,
                adult = false,
                backdropPath = "/path/to/backdrop.jpg",
                firstAirDate = "2024-08-25",
                genreIds = listOf(18, 35, 80), // Ejemplo de géneros: Drama, Comedia, Crimen
                name = "Example TV Show",
                originCountry = listOf("US", "GB"),
                originalLanguage = "en",
                originalName = "Example Original Name",
                overview = "This is an overview of the TV show. It provides a brief description of the plot and key details.",
                popularity = 6.5,
                posterPath = "/path/to/poster.jpg",
                voteAverage = 7.9,
                voteCount = 150,
                category = "Drama", // Categoría de la serie
                isFavorite = 1, // Ejemplo de estado de favorito: 1 para verdadero
            )

        tvResponse =
            TvResponse(
                adult = false,
                backdropPath = "/path/to/backdrop.jpg",
                firstAirDate = "2024-08-25",
                genreIds = listOf(18, 35, 80), // Ejemplo de géneros: Drama, Comedia, Crimen
                id = 789,
                name = "Example TV Show",
                originCountry = listOf("US", "GB"),
                originalLanguage = "en",
                originalName = "Example Original Name",
                overview = "This is an overview of the TV show. It provides a brief description of the plot and key details.",
                popularity = 6.5,
                posterPath = "/path/to/poster.jpg",
                voteAverage = 7.9,
                voteCount = 150,
            )
    }

    @Test
    fun `Give category and page When getTvs Then return result Tv`() {
        val response =
            BaseResponse(
                page = 1,
                results = listOf(tvResponse),
                totalPages = 1,
                totalResults = 1,
            )

        coEvery { dataSourceRemote.getTvByCategory("cat", 1) } returns response

        runBlocking {
            tvRepositoryImpl.getTvByCategory("cat", 1).apply {
                assert(isSuccess)
            }
        }

        validateSaveTv(category = "cat", response = response)

        coVerify { dataSourceRemote.getTvByCategory("cat", 1) }
    }

    @Test
    fun `Give error When getTvs Then return result failed`() {
        coEvery { dataSourceRemote.getTvByCategory("cat", 1) } throws Exception("Error")

        runBlocking {
            val result = tvRepositoryImpl.getTvByCategory("cat", 1)
            assert(result.isFailure)
            assert(result.exceptionOrNull() is DomainException)
        }

        coVerify { dataSourceRemote.getTvByCategory("cat", 1) }
    }

    @Test
    fun `Give movie When getTvById Then return result Tv`() {
        coEvery { db.tvDao().getTvById(1) } returns flowOf(tvEntity)
        runBlocking {
            val result = tvRepositoryImpl.getTvById(1)
            Assert.assertEquals(result.first(), tvEntity.toDomain())
        }
        coVerify { db.tvDao().getTvById(1) }
    }

    @Test
    fun `Give success When getTvDetailById Then return result Tv`() {
        val movie = mockk<TvDetailResponse>()
        coEvery { dataSourceRemote.getTvById(1) } returns movie
        every { movie.toDomain() } returns mockk()

        runBlocking {
            val result = tvRepositoryImpl.getTvDetailById(1)
            assert(result.isSuccess)
        }

        coVerify { dataSourceRemote.getTvById(1) }
        verify { movie.toDomain() }
    }

    @Test
    fun `Give success When getSearchTv Then return result Tv`() {
        val response =
            BaseResponse(
                page = 1,
                results = listOf(tvResponse),
                totalPages = 1,
                totalResults = 1,
            )

        coEvery { dataSourceRemote.getTvByQuery("query") } returns response

        runBlocking {
            tvRepositoryImpl.getSearchTv("query").apply {
                assert(isSuccess)
            }
        }
        validateSaveTv(category = "query", response = response)

        coVerify { dataSourceRemote.getTvByQuery("query") }
    }

    @Test
    fun `Give error When getSearchTv Then return result failed`() {
        coEvery { dataSourceRemote.getTvByQuery("query") } throws Exception("Error")

        runBlocking {
            tvRepositoryImpl.getSearchTv("query").apply {
                assert(isFailure)
                assert(exceptionOrNull() is DomainException)
            }
        }
        coVerify { dataSourceRemote.getTvByQuery("query") }
    }

    @Test
    fun `Give success When getFavoriteTvs Then return result Tv`() {
        val movieEntity = mockk<TvEntity>()
        coEvery { db.tvDao().getAllAsFlow() } returns flowOf(listOf(movieEntity))
        every { movieEntity.toDomain() } returns mockk<Tv>()

        runBlocking {
            tvRepositoryImpl.getFavoriteTv().firstOrNull()?.let {
                assert(it is List<Tv>)
                assert(it.isNotEmpty())
            }
        }

        verify {
            db.tvDao().getAllAsFlow()
            movieEntity.toDomain()
        }
    }

    @Test
    fun `Give success When updateTv Then return nothing`() {
        val movie =
            mockk<Tv> {
                every { id } returns 1
                every { isFavorite } returns true
            }
        coEvery { db.tvDao().updateTv(true, 1) } returns 1

        runBlocking {
            tvRepositoryImpl.updateTv(movie)
        }

        coVerify {
            db.tvDao().updateTv(true, 1)
        }
    }

    @Test
    fun `Give success When getAllCache Then return nothing`() {
        val movieEntity = mockk<TvEntity>()
        coEvery { db.tvDao().getAll() } returns flowOf(listOf(movieEntity))
        every { movieEntity.toDomain() } returns mockk<Tv>()

        runBlocking {
            tvRepositoryImpl.getTvByCategoryCache().firstOrNull()?.let {
                assert(it is List<Tv>)
                assert(it.isNotEmpty())
            }
        }

        verify {
            db.tvDao().getAll()
        }
    }

    private fun validateSaveTv(
        response: BaseResponse<TvResponse>,
        category: String,
    ) {
        val movieEntities = response.results.map { it.toEntity(category) }

        coEvery {
            db.tvDao().insertAll(movieEntities)
        } just runs

        runBlocking {
            val results = tvRepositoryImpl.saveTv(response, category)

            assert(results.results == response.results.map { it.toDomain(category) })
            assert(results.totalResults == response.totalResults)
            assert(results.page == response.page)
            assert(results.totalPages == response.totalPages)
        }

        coVerify { db.tvDao().insertAll(movieEntities) }
    }
}
