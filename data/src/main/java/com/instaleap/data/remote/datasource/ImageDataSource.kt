package com.instaleap.data.remote.datasource

import com.instaleap.data.remote.api.ImageApi
import com.instaleap.data.remote.response.ImageResponse
import com.instaleap.data.util.Constant
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
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
            val url = Constant.Api.IMAGE_URL.format(patch, id)
            val response = client.get(url)
            return response.body<ImageResponse>()
        }
    }
