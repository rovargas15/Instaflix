package com.instaleap.domain.usecase

import com.instaleap.domain.repository.MovieRepository

class GetMovieByCategoryUseCase(
    private val repository: MovieRepository,
) {
    suspend fun invoke(
        category: String,
        page: Int = 1,
    ) = repository.getMovies(category, page)
}
