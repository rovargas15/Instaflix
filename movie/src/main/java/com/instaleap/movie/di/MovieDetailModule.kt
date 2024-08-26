package com.instaleap.movie.di

import com.instaleap.domain.repository.MovieRepository
import com.instaleap.domain.usecase.GetImageByIdUseCase
import com.instaleap.domain.usecase.GetMovieByIdUseCase
import com.instaleap.domain.usecase.GetMovieDetailByIdUseCase
import com.instaleap.domain.usecase.UpdateMovieUseCase
import com.instaleap.movie.ui.detail.DetailViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object MovieDetailModule {
    @Provides
    @ViewModelScoped
    fun getMovieDetailByIdProvider(repository: MovieRepository) = GetMovieDetailByIdUseCase(repository)

    @Provides
    @ViewModelScoped
    fun getMovieByIdProvider(repository: MovieRepository) = GetMovieByIdUseCase(repository)

    @Provides
    @ViewModelScoped
    fun updateMovieProvider(repository: MovieRepository) = UpdateMovieUseCase(repository)

    @Provides
    @ViewModelScoped
    fun detailViewModelProvider(
        getDetailUseCase: GetMovieDetailByIdUseCase,
        getMovieByIdUseCase: GetMovieByIdUseCase,
        updateMovieUseCase: UpdateMovieUseCase,
        getImageByIdUseCase: GetImageByIdUseCase,
        coroutineDispatcher: CoroutineDispatcher,
    ) = DetailViewModel(
        getDetailUseCase = getDetailUseCase,
        getMovieByIdUseCase = getMovieByIdUseCase,
        updateMovieUseCase = updateMovieUseCase,
        getImageByIdUseCase = getImageByIdUseCase,
        coroutineDispatcher = coroutineDispatcher,
    )
}
