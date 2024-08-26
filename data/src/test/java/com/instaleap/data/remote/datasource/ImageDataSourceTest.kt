package com.instaleap.data.remote.datasource

import com.instaleap.data.clienteMockk
import com.instaleap.data.remote.response.ImageResponse
import com.instaleap.data.responseMockk
import io.ktor.client.engine.mock.MockEngine
import io.ktor.http.HttpStatusCode
import kotlin.test.Test
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows

class ImageDataSourceTest {
    private lateinit var imageDataSource: ImageDataSource

    @Test
    fun `Give success When getImageById Then return ImageResponse`() {
        val clientMockk =
            clienteMockk(
                MockEngine.invoke {
                    responseMockk(
                        content = "{\"id\": 550,\"backdrops\": [],\"logos\": [],\"posters\": []}",
                        status = HttpStatusCode.OK,
                    )
                },
            )

        imageDataSource = ImageDataSource(clientMockk)

        runBlocking {
            val expectedResponse =
                ImageResponse(
                    id = 550,
                    backdrops = emptyList(),
                    logos = emptyList(),
                    posters = emptyList(),
                )
            val actualResponse: ImageResponse = imageDataSource.getImageById(550, "movie")
            assertEquals(expectedResponse, actualResponse)
        }
    }

    @Test
    fun `Give error When getImageById Then return throw Throwable`() {
        val clientMockk =
            clienteMockk(
                MockEngine.invoke {
                    responseMockk(
                        status = HttpStatusCode.BadRequest,
                    )
                },
            )
        imageDataSource = ImageDataSource(clientMockk)

        assertThrows(Throwable::class.java) {
            runBlocking {
                imageDataSource.getImageById(550, "movie")
            }
        }
    }
}
