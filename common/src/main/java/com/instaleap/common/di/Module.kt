package com.instaleap.common.di

import com.instaleap.domain.repository.ImageRepository
import com.instaleap.domain.usecase.GetImageByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object Module {
    @Provides
    @ViewModelScoped
    fun getImageByIdProvider(repository: ImageRepository) = GetImageByIdUseCase(repository)
}
