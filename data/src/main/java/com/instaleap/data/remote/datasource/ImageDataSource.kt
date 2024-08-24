package com.instaleap.data.remote.datasource

import com.instaleap.data.remote.api.ImageApi
import com.instaleap.data.remote.response.ImageResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class ImageDataSource
    @Inject
    constructor(
        private val client: HttpClient,
    ) : ImageApi {
        override suspend fun getImageById(
            id: Int,
            patch: String,
        ): ImageResponse {
            val response = client.get("$patch/$id/images?language=es")
            if (response.status == HttpStatusCode.OK) {
                return response.body<ImageResponse>()
            } else {
                throw Throwable(response.status.description)
            }
        }
    }
