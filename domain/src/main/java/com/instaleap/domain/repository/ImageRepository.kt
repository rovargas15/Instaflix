package com.instaleap.domain.repository

import com.instaleap.domain.model.MovieImage

interface ImageRepository {

    suspend fun getMovieImageById(id: Int): Result<MovieImage>
}
