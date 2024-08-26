package com.instaleap.domain.usecase

import com.instaleap.domain.repository.TvRepository

class GetAllTvUseCase(
    private val repository: TvRepository,
) {
    suspend fun invoke() = repository.getTvByCategoryCache()
}
