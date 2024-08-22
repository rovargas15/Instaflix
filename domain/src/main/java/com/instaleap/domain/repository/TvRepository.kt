package com.instaleap.domain.repository

import com.instaleap.domain.model.DataBase
import com.instaleap.domain.model.Tv
import com.instaleap.domain.model.TvDetail
import kotlinx.coroutines.flow.Flow

interface TvRepository {
    suspend fun getTvByCategory(category: String): Result<DataBase<Tv>>

    suspend fun getTvById(id: Int): Flow<Tv>

    suspend fun getTvDetailById(id: Int): Result<TvDetail>

    suspend fun getSearchTv(query: String): Result<DataBase<Tv>>

    suspend fun getFavoriteTv(): Flow<List<Tv>>

    suspend fun updateTv(tv: Tv)

    suspend fun getPaginatedTv(
        category: String,
        pageSize: Int,
        page: Int,
    ): Result<DataBase<Tv>>
}
