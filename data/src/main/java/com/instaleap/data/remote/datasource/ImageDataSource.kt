package com.instaleap.data.remote.datasource

import com.instaleap.data.remote.api.ImageApi
import com.instaleap.data.remote.response.MovieImageResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode

class ImageDataSource(private val client: HttpClient) : ImageApi {

    override suspend fun getImageById(id: Int): MovieImageResponse {
        val response = client.get("movie/$id/images?language=es")
        if (response.status == HttpStatusCode.OK) {
            return response.body<MovieImageResponse>()
        } else {
            throw Throwable(response.status.description)
        }
    }
}
