package com.instaleap.favorite.di

import com.instaleap.domain.repository.MovieRepository
import com.instaleap.domain.repository.TvRepository
import com.instaleap.domain.usecase.GetFavoriteMovieUseCase
import com.instaleap.domain.usecase.GetFavoriteTvUseCase
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
    fun getFavoriteMovieProvider(repository: MovieRepository) = GetFavoriteMovieUseCase(repository = repository)

    @Provides
    @ViewModelScoped
    fun getFavoriteTvProvider(repository: TvRepository) = GetFavoriteTvUseCase(repository = repository)

    @Provides
    @ViewModelScoped
    fun favoriteViewModelProvider(
        getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
        getFavoriteTvUseCase: GetFavoriteTvUseCase,
        coroutineDispatcher: CoroutineDispatcher,
    ) = FavoriteViewModel(
        getFavoriteMovieUseCase = getFavoriteMovieUseCase,
        getFavoriteTvUseCase = getFavoriteTvUseCase,
        coroutineDispatcher = coroutineDispatcher,
    )
}
