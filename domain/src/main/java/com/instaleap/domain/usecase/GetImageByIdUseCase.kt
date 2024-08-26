package com.instaleap.domain.usecase

import com.instaleap.domain.model.Image
import com.instaleap.domain.repository.ImageRepository

class GetImageByIdUseCase(
    private val repository: ImageRepository,
) {
    suspend fun invoke(
        id: Int,
        path: String,
    ): Result<Image> =
        repository.getImageById(
            id = id,
            path = path,
        )
}
