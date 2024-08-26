package com.instaleap.data.remote.datasource

import com.instaleap.data.remote.api.MovieApi
import com.instaleap.data.remote.response.BaseResponse
import com.instaleap.data.remote.response.MovieDetailResponse
import com.instaleap.data.remote.response.MovieResponse
import com.instaleap.data.util.Constant
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class MovieDataSource
    @Inject
    constructor(
        private val client: HttpClient,
    ) : MovieApi {
        override suspend fun getMovies(
            category: String,
            page: Int,
        ): BaseResponse<MovieResponse> {
            val url = Constant.Api.MOVIE_BY_CATEGORY.format(category, page)
            val response = client.get(url)
            return response.body<BaseResponse<MovieResponse>>()
        }

        override suspend fun getMovieById(id: Int): MovieDetailResponse {
            val url = Constant.Api.MOVIE_BY_ID.format(id)

            val response = client.get(url)
            return response.body<MovieDetailResponse>()
        }

        override suspend fun getMoviesByQuery(query: String): BaseResponse<MovieResponse> {
            val url = Constant.Api.MOVIE_BY_QUERY.format(query)
            val response = client.get(url)
            return response.body<BaseResponse<MovieResponse>>()
        }
    }
