package com.instaleap.movie.di

import com.instaleap.domain.repository.MovieRepository
import com.instaleap.domain.usecase.GetAllMovieUseCase
import com.instaleap.domain.usecase.GetMovieByCategoryUseCase
import com.instaleap.movie.ui.movie.MovieViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object MovieModule {
    @Provides
    @ViewModelScoped
    fun getMovieByCategoryProvider(repository: MovieRepository) = GetMovieByCategoryUseCase(repository)

    @Provides
    @ViewModelScoped
    fun getAllMovieProvider(repository: MovieRepository) = GetAllMovieUseCase(repository)

    @Provides
    @ViewModelScoped
    fun movieViewModelProvider(
        getMovieByCategoryUseCase: GetMovieByCategoryUseCase,
        getAllMovieUseCase: GetAllMovieUseCase,
        coroutineDispatcher: CoroutineDispatcher,
    ) = MovieViewModel(
        getMovieByCategoryUseCase = getMovieByCategoryUseCase,
        getAllMovieUseCase = getAllMovieUseCase,
        coroutineDispatcher = coroutineDispatcher,
    )
}
