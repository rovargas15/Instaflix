package com.instaleap.domain.usecase

import com.instaleap.domain.repository.TvRepository

class GetTvByIdUseCase(
    private val repository: TvRepository,
) {
    suspend fun invoke(id: Int) = repository.getTvById(id)
}
