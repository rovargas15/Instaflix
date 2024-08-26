package com.instaleap.tv.di

import com.instaleap.domain.repository.TvRepository
import com.instaleap.domain.usecase.GetImageByIdUseCase
import com.instaleap.domain.usecase.GetTvByIdUseCase
import com.instaleap.domain.usecase.GetTvDetailByIdUseCase
import com.instaleap.domain.usecase.UpdateTvUseCase
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
    fun getTvDetailByIdProvider(repository: TvRepository) = GetTvDetailByIdUseCase(repository)

    @Provides
    @ViewModelScoped
    fun getTvByIdProvider(repository: TvRepository) = GetTvByIdUseCase(repository)

    @Provides
    @ViewModelScoped
    fun updateTvProvider(repository: TvRepository) = UpdateTvUseCase(repository)

    @Provides
    @ViewModelScoped
    fun detailViewModelProvider(
        getDetailUseCase: GetTvDetailByIdUseCase,
        getMovieById: GetTvByIdUseCase,
        updateTvUseCase: UpdateTvUseCase,
        getImageByIdUseCase: GetImageByIdUseCase,
        coroutineDispatcher: CoroutineDispatcher,
    ) = DetailViewModel(
        getDetailUseCase = getDetailUseCase,
        getMovieById = getMovieById,
        updateTvUseCase = updateTvUseCase,
        getImageByIdUseCase = getImageByIdUseCase,
        coroutineDispatcher = coroutineDispatcher,
    )
}
