package com.instaleap.favorite.di

import com.instaleap.domain.repository.MovieRepository
import com.instaleap.domain.repository.TvRepository
import com.instaleap.domain.usecase.GetFavoriteMovie
import com.instaleap.domain.usecase.GetFavoriteTv
import com.instaleap.favorite.ui.favorite.FavoriteViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object TvFavoriteModule {
    @Provides
    @ViewModelScoped
    fun getFavoriteMovieProvider(repository: MovieRepository) = GetFavoriteMovie(repository = repository)

    @Provides
    @ViewModelScoped
    fun getFavoriteTvProvider(repository: TvRepository) = GetFavoriteTv(repository = repository)

    @Provides
    @ViewModelScoped
    fun favoriteViewModelProvider(
        getFavoriteMovie: GetFavoriteMovie,
        getFavoriteTv: GetFavoriteTv,
        coroutineDispatcher: CoroutineDispatcher,
    ) = FavoriteViewModel(
        getFavoriteMovie = getFavoriteMovie,
        getFavoriteTv = getFavoriteTv,
        coroutineDispatcher = coroutineDispatcher,
    )
}
