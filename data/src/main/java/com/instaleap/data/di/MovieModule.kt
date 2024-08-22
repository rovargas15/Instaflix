package com.instaleap.data.di

import com.instaleap.data.local.database.InstaflixDatabase
import com.instaleap.data.remote.api.MovieApi
import com.instaleap.data.remote.datasource.MovieDataSource
import com.instaleap.data.repository.MovieRepositoryImpl
import com.instaleap.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient

@Module
@InstallIn(ViewModelComponent::class)
object MovieModule {

    @Provides
    @ViewModelScoped
    fun movieRepositoryImplProvider(
        dataSource: MovieDataSource,
        db: InstaflixDatabase,
    ): MovieRepository = MovieRepositoryImpl(
        dataSourceRemote = dataSource,
        db = db
    )

    @Provides
    @ViewModelScoped
    fun movieDataSourceProvider(
        client: HttpClient,
    ): MovieApi = MovieDataSource(
        client = client
    )
}