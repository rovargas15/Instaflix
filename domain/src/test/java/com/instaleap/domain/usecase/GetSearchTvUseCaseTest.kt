package com.instaleap.domain.usecase

import com.instaleap.domain.exception.UnknownException
import com.instaleap.domain.model.DataBase
import com.instaleap.domain.model.Tv
import com.instaleap.domain.repository.TvRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class GetSearchTvUseCaseTest {
    private val repository: TvRepository = mockk()
    private val getSearchTvUseCase = GetSearchTvUseCase(repository)

    @Test
    fun `Give query When invoke Then return result success`() =
        runBlocking {
            // Given
            val query = "query"
            val database = mockk<DataBase<Tv>>()
            coEvery { repository.getSearchTv(query) } returns Result.success(database)

            // When
            val result = getSearchTvUseCase.invoke(query)

            // Then
            assert(result.isSuccess)
            Assert.assertEquals(result.getOrNull(), database)
            coVerify { repository.getSearchTv(query) }
        }

    @Test
    fun `Give query When invoke Then return result failure`() =
        runBlocking {
            // Given
            val query = "query"
            val exception = UnknownException("error")
            coEvery {
                repository.getSearchTv(query)
            } returns Result.failure(exception)

            // When
            val result = getSearchTvUseCase.invoke(query)

            // Then
            assert(result.isFailure)
            Assert.assertEquals(exception, result.exceptionOrNull())
            coVerify { repository.getSearchTv(query) }
        }
}
