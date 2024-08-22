package com.instaleap.data.remote.api

import com.instaleap.data.remote.response.BaseResponse
import com.instaleap.data.remote.response.TvResponse
import com.instaleap.data.remote.response.TvDetailResponse

interface TvApi {

    suspend fun getTvByCategory(
        category: String = "",
        page: Int = 1,
    ): BaseResponse<TvResponse>

    suspend fun getTvById(id: Int): TvDetailResponse

    suspend fun getTvByQuery(query: String = ""): BaseResponse<TvResponse>
}