package com.instaleap.domain.usecase

import com.instaleap.domain.repository.TvRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.runBlocking
import org.junit.Test

class UpdateTvTest {
    private val repository: TvRepository = mockk()
    private val getTvById = UpdateTv(repository)

    @Test
    fun `Give tv When invoke Then nothing`() =
        runBlocking {
            // Given
            coEvery { repository.updateTv(any()) } just runs

            // When
            getTvById.invoke(mockk())

            // Then
            coVerify { repository.updateTv(any()) }
        }
}
