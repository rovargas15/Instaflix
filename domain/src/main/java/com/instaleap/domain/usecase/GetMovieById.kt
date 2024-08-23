package com.instaleap.domain.usecase

import com.instaleap.domain.repository.MovieRepository

class GetMovieById(
    private val repository: MovieRepository,
) {
    suspend fun invoke(id: Int) = repository.getMovieById(id)
}
