package com.instaleap.data.repository

import com.instaleap.data.remote.datasource.ImageDataSource
import com.instaleap.data.remote.response.ImageResponse
import com.instaleap.domain.exception.DomainException
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.any
import org.junit.Before
import kotlin.test.Test

class ImageRepositoryImplTest {
    @MockK
    lateinit var imageDataSource: ImageDataSource

    @InjectMockKs
    lateinit var imageRepositoryImpl: ImageRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `Give success When getImageById Then return Image`() {
        val imageResponse =
            ImageResponse(
                backdrops =
                    listOf(
                        ImageResponse.PosterResponse(
                            aspectRatio = 1.77,
                            filePath = "/path/to/backdrop1.jpg",
                            height = 1080,
                            iso6391 = "en",
                            voteAverage = 8.5,
                            voteCount = 200,
                            width = 1920,
                        ),
                        ImageResponse.PosterResponse(
                            aspectRatio = 1.78,
                            filePath = "/path/to/backdrop2.jpg",
                            height = 720,
                            iso6391 = "es",
                            voteAverage = 7.5,
                            voteCount = 150,
                            width = 1280,
                        ),
                    ),
                id = 123,
                logos =
                    listOf(
                        ImageResponse.PosterResponse(
                            aspectRatio = 1.0,
                            filePath = "/path/to/logo1.png",
                            height = 100,
                            iso6391 = "en",
                            voteAverage = null,
                            voteCount = null,
                            width = 100,
                        ),
                    ),
                posters =
                    listOf(
                        ImageResponse.PosterResponse(
                            aspectRatio = 0.67,
                            filePath = "/path/to/poster1.jpg",
                            height = 600,
                            iso6391 = "fr",
                            voteAverage = 8.0,
                            voteCount = 180,
                            width = 400,
                        ),
                    ),
            )
        coEvery { imageDataSource.getImageById(any(), any()) } returns imageResponse

        runBlocking {
            val result = imageRepositoryImpl.getImageById(1, "")
            assert(result.isSuccess)
            assert(result.getOrNull() == imageResponse.toDomain())
        }

        coVerify { imageDataSource.getImageById(any(), any()) }
    }

    @Test
    fun `Give error When getImageById Then return DomainException`() {
        coEvery { imageDataSource.getImageById(any(), any()) } throws Exception()
        runBlocking {
            val result = imageRepositoryImpl.getImageById(1, "")
            assert(result.isFailure)
            assert(result.exceptionOrNull() is DomainException)
        }
        coVerify { imageDataSource.getImageById(any(), any()) }
    }
}
