package com.instaleap.data.remote.datasource

import com.instaleap.data.remote.api.MovieApi
import com.instaleap.data.remote.response.BaseResponse
import com.instaleap.data.remote.response.MovieDetailResponse
import com.instaleap.data.remote.response.MovieResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import javax.inject.Inject

class MovieDataSource  @Inject constructor(private val client: HttpClient) : MovieApi {
    override suspend fun getMovies(
        category: String,
        page: Int,
    ): BaseResponse<MovieResponse> {
        val response = client.get("movie/$category?language=es&page=$page")
        if (response.status == HttpStatusCode.OK) {
            return response.body<BaseResponse<MovieResponse>>()
        } else {
            throw Throwable(response.status.description)
        }
    }

    override suspend fun getMovieById(id: Int): MovieDetailResponse {
        val response = client.get("movie/$id?language=es")
        if (response.status == HttpStatusCode.OK) {
            return response.body<MovieDetailResponse>()
        } else {
            throw Throwable(response.status.description)
        }
    }

    override suspend fun getMoviesByQuery(query: String): BaseResponse<MovieResponse> {
        val response = client.get("search/movie?query=$query?&language=es")
        if (response.status == HttpStatusCode.OK) {
            return response.body<BaseResponse<MovieResponse>>()
        } else {
            throw Throwable(response.status.description)
        }
    }
}
