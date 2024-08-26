package com.instaleap.domain.usecase

import com.instaleap.domain.repository.MovieRepository

class GetFavoriteMovieUseCase(
    private val repository: MovieRepository,
) {
    suspend fun invoke() = repository.getFavoriteMovie()
}
