package com.instaleap.domain.usecase

import com.instaleap.domain.model.Image
import com.instaleap.domain.repository.ImageRepository

class GetImageById(
    private val repository: ImageRepository,
) {
    suspend fun invoke(id: Int): Result<Image> = repository.getMovieImageById(id)
}
