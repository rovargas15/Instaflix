package com.instaleap.domain.usecase

import com.instaleap.domain.model.Tv
import com.instaleap.domain.repository.TvRepository

class UpdateTvUseCase(
    private val repository: TvRepository,
) {
    suspend fun invoke(tv: Tv) = repository.updateTv(tv)
}
