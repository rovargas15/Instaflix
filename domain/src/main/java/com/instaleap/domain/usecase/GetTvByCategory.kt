package com.instaleap.domain.usecase

import com.instaleap.domain.repository.MovieRepository
import com.instaleap.domain.repository.TvRepository

class GetTvByCategory(private val repository: TvRepository) {
    suspend fun invoke(category: String) = repository.getTvByCategory(category)
}
