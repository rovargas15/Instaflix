package com.instaleap.domain.usecase

import com.instaleap.domain.repository.TvRepository


class GetFavoriteTv(private val repository: TvRepository) {
    suspend fun invoke() = repository.getFavoriteTv()
}
