package com.instaleap.domain.usecase

import com.instaleap.domain.repository.MovieRepository

class GetAllMovie(
    private val repository: MovieRepository,
) {
    suspend fun invoke() = repository.getAllCache()
}
