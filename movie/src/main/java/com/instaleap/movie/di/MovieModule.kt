package com.instaleap.movie.di

import com.instaleap.domain.repository.MovieRepository
import com.instaleap.domain.usecase.GetAllMovie
import com.instaleap.domain.usecase.GetMovieByCategory
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
    fun getMovieByCategoryProvider(repository: MovieRepository) = GetMovieByCategory(repository)

    @Provides
    @ViewModelScoped
    fun getAllMovieProvider(repository: MovieRepository) = GetAllMovie(repository)

    @Provides
    @ViewModelScoped
    fun movieViewModelProvider(
        getMovieByCategory: GetMovieByCategory,
        getAllMovie: GetAllMovie,
        coroutineDispatcher: CoroutineDispatcher,
    ) = MovieViewModel(
        getMovieByCategory = getMovieByCategory,
        getAllMovie = getAllMovie,
        coroutineDispatcher = coroutineDispatcher,
    )
}
