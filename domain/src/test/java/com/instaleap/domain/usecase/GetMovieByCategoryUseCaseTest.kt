package com.instaleap.domain.usecase

import com.instaleap.domain.exception.UnknownException
import com.instaleap.domain.model.DataBase
import com.instaleap.domain.model.Movie
import com.instaleap.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetMovieByCategoryUseCaseTest {
    private val repository: MovieRepository = mockk()
    private val getMovieByCategoryUseCase = GetMovieByCategoryUseCase(repository)

    @Test
    fun `Give category and page When invoke Then return result movie`() =
        runBlocking {
            // Given
            val response = mockk<DataBase<Movie>>()
            coEvery { repository.getMovies("movie", 2) } returns Result.success(response)

            // When
            val result = getMovieByCategoryUseCase.invoke("movie", 2)

            // Then
            assert(result.isSuccess)
            assertEquals(response, result.getOrNull())
            coVerify { repository.getMovies("movie", 2) }
        }

    @Test
    fun `Give category  When invoke Then return result movie`() =
        runBlocking {
            // Given
            val response = mockk<DataBase<Movie>>()
            coEvery { repository.getMovies("movie", 1) } returns Result.success(response)

            // When
            val result = getMovieByCategoryUseCase.invoke("movie")

            // Then
            assert(result.isSuccess)
            assertEquals(response, result.getOrNull())
            coVerify { repository.getMovies("movie", 1) }
        }

    @Test
    fun `Give category and page When invoke Then return result failure`() =
        runBlocking {
            // Given
            val exception = UnknownException("error")
            coEvery { repository.getMovies("movie", 1) } returns Result.failure(exception)

            // When
            val result = getMovieByCategoryUseCase.invoke("movie")

            // Then
            assert(result.isFailure)
            assertEquals(result.exceptionOrNull(), exception)
            coVerify { repository.getMovies("movie", 1) }
        }
}
