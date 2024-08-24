package com.instaleap.tv.di

import com.instaleap.domain.repository.TvRepository
import com.instaleap.domain.usecase.GetImageById
import com.instaleap.domain.usecase.GetTvById
import com.instaleap.domain.usecase.GetTvDetailById
import com.instaleap.domain.usecase.UpdateTv
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
    fun getTvByIdProvider(repository: TvRepository) = GetTvById(repository)

    @Provides
    @ViewModelScoped
    fun updateTvProvider(repository: TvRepository) = UpdateTv(repository)

    @Provides
    @ViewModelScoped
    fun detailViewModelProvider(
        getDetailUseCase: GetTvDetailById,
        getMovieById: GetTvById,
        updateTv: UpdateTv,
        getImageById: GetImageById,
        coroutineDispatcher: CoroutineDispatcher,
    ) = DetailViewModel(
        getDetailUseCase = getDetailUseCase,
        getMovieById = getMovieById,
        updateTv = updateTv,
        getImageById = getImageById,
        coroutineDispatcher = coroutineDispatcher,
    )
}
