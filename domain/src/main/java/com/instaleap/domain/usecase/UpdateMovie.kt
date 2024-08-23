package com.instaleap.domain.usecase

import com.instaleap.domain.model.Movie
import com.instaleap.domain.repository.MovieRepository

class UpdateMovie(
    private val repository: MovieRepository,
) {
    suspend fun invoke(movie: Movie) = repository.updateMovie(movie)
}
