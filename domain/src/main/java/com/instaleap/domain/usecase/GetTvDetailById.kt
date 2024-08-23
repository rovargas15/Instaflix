package com.instaleap.domain.usecase

import com.instaleap.domain.model.TvDetail
import com.instaleap.domain.repository.TvRepository

class GetTvDetailById(private val repository: TvRepository) {
    suspend fun invoke(id: Int): Result<TvDetail> = repository.getTvDetailById(id)
}
