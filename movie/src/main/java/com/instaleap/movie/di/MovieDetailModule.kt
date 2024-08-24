package com.instaleap.movie.di

import com.instaleap.domain.repository.MovieRepository
import com.instaleap.domain.usecase.GetImageById
import com.instaleap.domain.usecase.GetMovieById
import com.instaleap.domain.usecase.GetMovieDetailById
import com.instaleap.domain.usecase.UpdateMovie
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
    fun getMovieDetailByIdProvider(repository: MovieRepository) = GetMovieDetailById(repository)

    @Provides
    @ViewModelScoped
    fun getMovieByIdProvider(repository: MovieRepository) = GetMovieById(repository)

    @Provides
    @ViewModelScoped
    fun updateMovieProvider(repository: MovieRepository) = UpdateMovie(repository)

    @Provides
    @ViewModelScoped
    fun detailViewModelProvider(
        getDetailUseCase: GetMovieDetailById,
        getMovieById: GetMovieById,
        updateMovie: UpdateMovie,
        getImageById: GetImageById,
        coroutineDispatcher: CoroutineDispatcher,
    ) = DetailViewModel(
        getDetailUseCase = getDetailUseCase,
        getMovieById = getMovieById,
        updateMovie = updateMovie,
        getImageById = getImageById,
        coroutineDispatcher = coroutineDispatcher,
    )
}
