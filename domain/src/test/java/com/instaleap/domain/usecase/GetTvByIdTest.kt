package com.instaleap.domain.usecase

import com.instaleap.domain.model.Tv
import com.instaleap.domain.repository.TvRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetTvByIdTest {
    private val repository: TvRepository = mockk()
    private val getTvById = GetTvById(repository)

    @Test
    fun `Give success When invoke Then return tv list`() =
        runBlocking {
            // Given
            coEvery { repository.getTvById(1) } returns flowOf(mockk())

            // When
            val result = getTvById.invoke(1)

            // Then
            assert(result.firstOrNull() is Tv)
            coVerify { repository.getTvById(1) }
        }
}
