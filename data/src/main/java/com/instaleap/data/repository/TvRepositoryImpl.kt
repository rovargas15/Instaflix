package com.instaleap.data.repository

import com.instaleap.data.local.database.InstaflixDatabase
import com.instaleap.data.remote.datasource.TvDataSource
import com.instaleap.data.remote.response.BaseResponse
import com.instaleap.data.remote.response.TvResponse
import com.instaleap.data.util.BaseRepository
import com.instaleap.domain.model.DataBase
import com.instaleap.domain.model.Tv
import com.instaleap.domain.model.TvDetail
import com.instaleap.domain.repository.TvRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TvRepositoryImpl
    @Inject
    constructor(
        private val dataSourceRemote: TvDataSource,
        private val db: InstaflixDatabase,
    ) : BaseRepository(),
        TvRepository {
        override suspend fun getTvByCategory(category: String): Result<DataBase<Tv>> =
            launchResultSafe {
                saveTv(dataSourceRemote.getTvByCategory(category), category)
            }

        override suspend fun getTvByCategoryCache(): Flow<List<Tv>> =
            db.tvDao().getAll().map { resultsChange ->
                resultsChange.map { it.toDomain() }
            }

        override suspend fun getTvById(id: Int): Flow<Tv> = db.tvDao().getTvById(id).map { resultsChange -> resultsChange.toDomain() }

        override suspend fun getTvDetailById(id: Int): Result<TvDetail> =
            launchResultSafe {
                dataSourceRemote.getTvById(id).toDomain()
            }

        override suspend fun getSearchTv(query: String): Result<DataBase<Tv>> =
            launchResultSafe {
                saveTv(dataSourceRemote.getTvByQuery(query))
            }

        override suspend fun getFavoriteTv(): Flow<List<Tv>> =
            db.tvDao().getAllAsFlow().map { resultsChange ->
                resultsChange.map { it.toDomain() }
            }

        override suspend fun updateTv(tv: Tv) {
            db.tvDao().updateTv(tv.isFavorite, tv.id)
        }

        override suspend fun getPaginatedTv(
            category: String,
            pageSize: Int,
            page: Int,
        ): Result<DataBase<Tv>> =
            launchResultSafe {
                saveTv(dataSourceRemote.getTvByCategory(category, page), category)
            }

        private suspend fun saveTv(
            response: BaseResponse<TvResponse>,
            category: String = "",
        ): DataBase<Tv> {
            response.results.map { it.toEntity(category) }.also { series ->
                db.tvDao().insertAll(series)
            }
            return DataBase(
                page = response.page,
                results = response.results.map { it.toDomain() },
                totalPages = response.totalPages,
                totalResults = response.totalResults,
            )
        }
    }
