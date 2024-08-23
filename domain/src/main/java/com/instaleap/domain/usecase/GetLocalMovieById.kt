package com.instaleap.domain.usecase

import com.instaleap.domain.repository.MovieRepository

class GetLocalMovieById(
    private val repository: MovieRepository,
) {
    suspend fun invoke(id: Int) = repository.getMovieById(id)
}
