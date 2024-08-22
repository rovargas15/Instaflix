package com.instaleap.data.remote.api

import com.instaleap.data.remote.response.MovieImageResponse

interface ImageApi {

    suspend fun getImageById(id: Int): MovieImageResponse
}