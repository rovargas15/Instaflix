package com.instaleap.data.remote.datasource

import com.instaleap.data.remote.api.TvApi
import com.instaleap.data.remote.response.BaseResponse
import com.instaleap.data.remote.response.TvDetailResponse
import com.instaleap.data.remote.response.TvResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class TvDataSource @Inject constructor(private val client: HttpClient) : TvApi {
    override suspend fun getTvByCategory(
        category: String,
        page: Int,
    ): BaseResponse<TvResponse> {
        val response = client.get("tv/$category?language=es&page=$page")
        if (response.status == HttpStatusCode.OK) {
            return response.body<BaseResponse<TvResponse>>()
        } else {
            throw Throwable(response.status.description)
        }
    }

    override suspend fun getTvById(id: Int): TvDetailResponse {
        val response = client.get("tv/$id?language=es")
        if (response.status == HttpStatusCode.OK) {
            return response.body<TvDetailResponse>()
        } else {
            throw Throwable(response.status.description)
        }
    }

    override suspend fun getTvByQuery(query: String): BaseResponse<TvResponse> {
        val response = client.get("search/tv?query=$query?&language=es")
        if (response.status == HttpStatusCode.OK) {
            return response.body<BaseResponse<TvResponse>>()
        } else {
            throw Throwable(response.status.description)
        }
    }
}
