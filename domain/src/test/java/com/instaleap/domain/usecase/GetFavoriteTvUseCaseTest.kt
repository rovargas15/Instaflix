package com.instaleap.domain.usecase

import com.instaleap.domain.model.Tv
import com.instaleap.domain.repository.TvRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetFavoriteTvUseCaseTest {
    private val repository: TvRepository = mockk()
    private val getAllTvUseCase = GetAllTvUseCase(repository)

    @Test
    fun `Give success When invoke Then return tv list`() =
        runBlocking {
            // Given
            val tvList = listOf<Tv>(mockk())
            coEvery { repository.getTvByCategoryCache() } returns flowOf(tvList)

            // When
            val result = getAllTvUseCase.invoke()

            // Then
            assertEquals(tvList, result.firstOrNull())
            coVerify { repository.getTvByCategoryCache() }
        }
}
