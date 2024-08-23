package com.instaleap.domain.repository

import com.instaleap.domain.model.Image

interface ImageRepository {

    suspend fun getMovieImageById(id: Int): Result<Image>
}
