package com.instaleap.domain.usecase

import com.instaleap.domain.exception.UnknownException
import com.instaleap.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetSearchMovieUseCaseTest {
    private val repository: MovieRepository = mockk()
    private val getSearchMovieUseCase = GetSearchMovieUseCase(repository)

    @Test
    fun `Give query When invoke Then return result success`() =
        runBlocking {
            // Given
            val query = "query"
            coEvery { repository.getSearchMovie(query) } returns Result.success(mockk())

            // When
            val result = getSearchMovieUseCase.invoke(query)

            // Then
            assert(result.isSuccess)
            coVerify { repository.getSearchMovie(query) }
        }

    @Test
    fun `Give query When invoke Then return result failure`() =
        runBlocking {
            // Given
            val query = "query"
            val exception = UnknownException("error")
            coEvery {
                repository.getSearchMovie(query)
            } returns Result.failure(exception)

            // When
            val result = getSearchMovieUseCase.invoke(query)

            // Then
            assert(result.isFailure)
            coVerify { repository.getSearchMovie(query) }
        }
}
