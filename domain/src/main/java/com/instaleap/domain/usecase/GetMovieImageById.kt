package com.instaleap.domain.usecase

import com.instaleap.domain.model.MovieImage
import com.instaleap.domain.repository.ImageRepository

class GetMovieImageById(private val repository: ImageRepository) {
    suspend fun invoke(id: Int): Result<MovieImage> = repository.getMovieImageById(id)
}
