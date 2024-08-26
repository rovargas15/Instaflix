package com.instaleap.data.di

import com.instaleap.data.remote.api.ImageApi
import com.instaleap.data.remote.datasource.ImageDataSource
import com.instaleap.data.repository.ImageRepositoryImpl
import com.instaleap.domain.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.ktor.client.HttpClient

@Module
@InstallIn(ViewModelComponent::class)
object ImageModule {
    @Provides
    @ViewModelScoped
    fun imageRepositoryImplProvider(dataSource: ImageDataSource): ImageRepository =
        ImageRepositoryImpl(
            imageDataSource = dataSource,
        )

    @Provides
    @ViewModelScoped
    fun imageDataSourceProvider(client: HttpClient): ImageApi =
        ImageDataSource(
            client = client,
        )
}
