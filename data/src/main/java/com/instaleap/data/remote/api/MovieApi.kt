package com.instaleap.data.remote.api

import com.instaleap.data.remote.response.BaseResponse
import com.instaleap.data.remote.response.MovieDetailResponse
import com.instaleap.data.remote.response.MovieResponse

interface MovieApi {
    suspend fun getMovies(
        category: String = "",
        page: Int = 1,
    ): BaseResponse<MovieResponse>

    suspend fun getMovieById(id: Int): MovieDetailResponse

    suspend fun getMoviesByQuery(query: String = ""): BaseResponse<MovieResponse>
}
