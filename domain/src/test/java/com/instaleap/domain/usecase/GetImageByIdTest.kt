package com.instaleap.domain.usecase

import com.instaleap.domain.exception.UnknownException
import com.instaleap.domain.model.Image
import com.instaleap.domain.repository.ImageRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetImageByIdTest {
    private val repository: ImageRepository = mockk()
    private val getImageById = GetImageById(repository)

    private val id = 1
    private val type = "movie"

    @Test
    fun `Give movie When invoke Then return result image`() =
        runBlocking {
            // Given
            val image = mockk<Image>()
            coEvery { repository.getImageById(id, type) } returns Result.success(image)

            // When
            val result = getImageById.invoke(id, type)

            // Then
            assert(result.isSuccess)
            assertEquals(image, result.getOrNull())
            coVerify { repository.getImageById(id, type) }
        }

    @Test
    fun `Give error When invoke Then return result failure`() =
        runBlocking {
            // Given
            val exception = UnknownException("error")
            coEvery {
                repository.getImageById(
                    id,
                    type,
                )
            } returns Result.failure(exception)

            // When
            val result = getImageById.invoke(id, type)

            // Then
            assert(result.isFailure)
            assertEquals(result.exceptionOrNull(), exception)
            coVerify { repository.getImageById(1, type) }
        }
}
