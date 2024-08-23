package com.instaleap.domain.usecase

import com.instaleap.domain.repository.TvRepository

class GetTvById(
    private val repository: TvRepository,
) {
    suspend fun invoke(id: Int) = repository.getTvById(id)
}
