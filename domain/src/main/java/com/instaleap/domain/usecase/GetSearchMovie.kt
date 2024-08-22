package com.instaleap.domain.usecase

import com.instaleap.domain.repository.MovieRepository

class GetSearchMovie(private val repository: MovieRepository) {
    suspend fun invoke(search: String) = repository.getSearchMovie(search)
}
