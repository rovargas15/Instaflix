package com.instaleap.domain.usecase

import com.instaleap.domain.repository.TvRepository

class GetSearchTv(private val repository: TvRepository) {
    suspend fun invoke(search: String) = repository.getSearchTv(search)
}
