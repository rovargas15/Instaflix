package com.instaleap.domain.usecase

import com.instaleap.domain.exception.UnknownException
import com.instaleap.domain.repository.MovieRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetMovieDetailByIdTest {
    private val repository: MovieRepository = mockk()
    private val getMovieById = GetMovieDetailById(repository)

    @Test
    fun `Give id When invoke Then return success`() =
        runBlocking {
            // Given
            val id = 1
            coEvery { repository.getMovieDetailById(id) } returns Result.success(mockk())

            // When
            val result = getMovieById.invoke(id)

            // Then
            assert(result.isSuccess)
            coVerify { repository.getMovieDetailById(id) }
        }

    @Test
    fun `Give id When invoke Then return failure`() =
        runBlocking {
            // Given
            val id = 1
            coEvery { repository.getMovieDetailById(id) } returns Result.failure(UnknownException())

            // When
            val result = getMovieById.invoke(id)

            // Then
            assert(result.isFailure)
            coVerify { repository.getMovieDetailById(id) }
        }
}
