package com.instaleap.domain.usecase

import com.instaleap.domain.exception.UnknownException
import com.instaleap.domain.model.DataBase
import com.instaleap.domain.model.Tv
import com.instaleap.domain.repository.TvRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetTvByCategoryTest {
    private val repository: TvRepository = mockk()
    private val getTvByCategory = GetTvByCategory(repository)

    @Test
    fun `Give category  When invoke Then return result tv`() =
        runBlocking {
            // Given
            val response = mockk<DataBase<Tv>>()
            coEvery { repository.getTvByCategory("tv", 1) } returns Result.success(response)

            // When
            val result = getTvByCategory.invoke("tv")

            // Then
            assert(result.isSuccess)
            assertEquals(response, result.getOrNull())
            coVerify { repository.getTvByCategory("tv", 1) }
        }

    @Test
    fun `Give category and page  When invoke Then return result tv`() =
        runBlocking {
            // Given
            val response = mockk<DataBase<Tv>>()
            coEvery { repository.getTvByCategory("tv", 2) } returns Result.success(response)

            // When
            val result = getTvByCategory.invoke("tv", 2)

            // Then
            assert(result.isSuccess)
            assertEquals(response, result.getOrNull())
            coVerify { repository.getTvByCategory("tv", 2) }
        }

    @Test
    fun `Give error When invoke Then return result failure`() =
        runBlocking {
            // Given
            val exception = UnknownException("error")
            coEvery { repository.getTvByCategory("tv", 1) } returns Result.failure(exception)

            // When
            val result = getTvByCategory.invoke("tv")

            // Then
            assert(result.isFailure)
            assertEquals(result.exceptionOrNull(), exception)
            coVerify { repository.getTvByCategory("tv", 1) }
        }
}
