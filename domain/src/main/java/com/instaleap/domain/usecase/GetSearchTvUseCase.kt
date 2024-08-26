package com.instaleap.domain.usecase

import com.instaleap.domain.repository.TvRepository

class GetSearchTvUseCase(private val repository: TvRepository) {
    suspend fun invoke(search: String) = repository.getSearchTv(search)
}
