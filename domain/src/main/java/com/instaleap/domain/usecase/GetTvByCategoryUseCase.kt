package com.instaleap.domain.usecase

import com.instaleap.domain.repository.TvRepository

class GetTvByCategoryUseCase(
    private val repository: TvRepository,
) {
    suspend fun invoke(
        category: String,
        page: Int = 1,
    ) = repository.getTvByCategory(category, page)
}
