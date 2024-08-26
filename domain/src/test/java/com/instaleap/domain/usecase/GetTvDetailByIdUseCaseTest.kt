package com.instaleap.domain.usecase

import com.instaleap.domain.exception.UnknownException
import com.instaleap.domain.repository.TvRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetTvDetailByIdUseCaseTest {
    private val repository: TvRepository = mockk()
    private val getTvById = GetTvDetailByIdUseCase(repository)

    @Test
    fun `Give id When invoke Then return success`() =
        runBlocking {
            // Given
            val id = 1
            coEvery { repository.getTvDetailById(id) } returns Result.success(mockk())

            // When
            val result = getTvById.invoke(id)

            // Then
            assert(result.isSuccess)
            coVerify { repository.getTvDetailById(id) }
        }

    @Test
    fun `Give id When invoke Then return failure`() =
        runBlocking {
            // Given
            val id = 1
            coEvery { repository.getTvDetailById(id) } returns Result.failure(UnknownException())

            // When
            val result = getTvById.invoke(id)

            // Then
            assert(result.isFailure)
            coVerify { repository.getTvDetailById(id) }
        }
}
