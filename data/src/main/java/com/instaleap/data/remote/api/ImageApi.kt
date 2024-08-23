package com.instaleap.data.remote.api

import com.instaleap.data.remote.response.ImageResponse

interface ImageApi {

    suspend fun getImageById(id: Int): ImageResponse
}