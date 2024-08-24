package com.instaleap.domain.repository

import com.instaleap.domain.model.Image

interface ImageRepository {
    suspend fun getImageById(
        id: Int,
        path: String,
    ): Result<Image>
}
