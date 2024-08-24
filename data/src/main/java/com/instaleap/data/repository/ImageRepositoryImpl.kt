package com.instaleap.data.repository

import com.instaleap.data.remote.datasource.ImageDataSource
import com.instaleap.data.util.BaseRepository
import com.instaleap.domain.model.Image
import com.instaleap.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl
    @Inject
    constructor(
        private val imageDataSource: ImageDataSource,
    ) : BaseRepository(),
        ImageRepository {
        override suspend fun getImageById(
            id: Int,
            path: String,
        ): Result<Image> =
            launchResultSafe {
                imageDataSource.getImageById(id, path).toDomain()
            }
    }
