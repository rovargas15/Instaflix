package com.instaleap.domain.usecase

import com.instaleap.domain.model.MovieDetail
import com.instaleap.domain.repository.MovieRepository

class GetMovieDetailByIdUseCase(
    private val repository: MovieRepository,
) {
    suspend fun invoke(id: Int): Result<MovieDetail> = repository.getMovieDetailById(id)
}
