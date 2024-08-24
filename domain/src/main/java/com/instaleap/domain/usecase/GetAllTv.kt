package com.instaleap.domain.usecase

import com.instaleap.domain.repository.TvRepository

class GetAllTv(
    private val repository: TvRepository,
) {
    suspend fun invoke() = repository.getTvByCategoryCache()
}
