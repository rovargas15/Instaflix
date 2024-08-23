package com.instaleap.movie.di

import com.instaleap.domain.repository.MovieRepository
import com.instaleap.domain.usecase.GetMovieDetailById
import com.instaleap.presentation.movie.detail.DetailViewModel
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
    fun getMovieDetailByIdProvider(repository: MovieRepository) = GetMovieDetailById(repository)

    @Provides
    @ViewModelScoped
    fun detailViewModelProvider(
        getDetailUseCase: GetMovieDetailById,
        coroutineDispatcher: CoroutineDispatcher,
    ) = DetailViewModel(getDetailUseCase, coroutineDispatcher)
}
