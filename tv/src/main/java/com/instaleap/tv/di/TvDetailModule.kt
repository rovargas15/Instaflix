package com.instaleap.tv.di

import com.instaleap.domain.repository.TvRepository
import com.instaleap.domain.usecase.GetTvDetailById
import com.instaleap.tv.ui.detail.DetailViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object TvDetailModule {
    @Provides
    @ViewModelScoped
    fun getTvDetailByIdProvider(repository: TvRepository) = GetTvDetailById(repository)

    @Provides
    @ViewModelScoped
    fun detailViewModelProvider(
        getDetailUseCase: GetTvDetailById,
        coroutineDispatcher: CoroutineDispatcher,
    ) = DetailViewModel(getDetailUseCase, coroutineDispatcher)
}
