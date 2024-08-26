package com.instaleap.domain.usecase

import com.instaleap.domain.repository.MovieRepository

class GetAllMovieUseCase(
    private val repository: MovieRepository,
) {
    suspend fun invoke() = repository.getAllCache()
}
