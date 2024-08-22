package com.instaleap.data.di

import com.instaleap.data.local.database.InstaflixDatabase
import com.instaleap.data.remote.api.TvApi
import com.instaleap.data.remote.datasource.TvDataSource
import com.instaleap.data.repository.TvRepositoryImpl
import com.instaleap.domain.repository.TvRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient

@Module
@InstallIn(ViewModelComponent::class)
object TvModule {

    @Provides
    @ViewModelScoped
    fun tvRepositoryImplProvider(
        dataSource: TvDataSource,
        db: InstaflixDatabase,
    ): TvRepository = TvRepositoryImpl(
        dataSourceRemote = dataSource,
        db = db
    )

    @Provides
    @ViewModelScoped
    fun tvDataSourceProvider(
        client: HttpClient,
    ): TvApi = TvDataSource(
        client = client
    )
}