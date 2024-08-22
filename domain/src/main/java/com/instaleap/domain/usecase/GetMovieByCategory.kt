package com.instaleap.domain.usecase

import com.instaleap.domain.repository.MovieRepository

class GetMovieByCategory(private val repository: MovieRepository) {
    suspend fun invoke(category: String) = repository.getMovies(category)
}
