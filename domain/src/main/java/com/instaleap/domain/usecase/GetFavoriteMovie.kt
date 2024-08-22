package com.instaleap.domain.usecase

import com.instaleap.domain.repository.MovieRepository


class GetFavoriteMovie(private val repository: MovieRepository) {
    suspend fun invoke() = repository.getFavoriteMovie()
}
