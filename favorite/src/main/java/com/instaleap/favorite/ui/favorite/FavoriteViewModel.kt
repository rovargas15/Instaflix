package com.instaleap.favorite.ui.favorite

import androidx.lifecycle.viewModelScope
import com.instaleap.core.MviViewModel
import com.instaleap.domain.model.Movie
import com.instaleap.domain.model.Tv
import com.instaleap.domain.usecase.GetFavoriteMovieUseCase
import com.instaleap.domain.usecase.GetFavoriteTvUseCase
import com.instaleap.favorite.ui.favorite.FavoriteContract.Effect
import com.instaleap.favorite.ui.favorite.FavoriteContract.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel
    @Inject
    constructor(
        private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
        private val getFavoriteTvUseCase: GetFavoriteTvUseCase,
        private val coroutineDispatcher: CoroutineDispatcher,
    ) : MviViewModel<FavoriteContract.UiState, UiEvent, Effect>() {
        override fun initialState() = FavoriteContract.UiState()

        fun fetchData() {
            updateState {
                copy(isLoading = true)
            }
            viewModelScope.launch(coroutineDispatcher) {
                getFavoriteMovieUseCase
                    .invoke()
                    .combine(getFavoriteTvUseCase.invoke()) { movie, tv ->
                        mapOf(1 to movie, 2 to tv)
                    }.collect {
                        updateState {
                            copy(
                                listMovies = it.getValue(1) as List<Movie>,
                                listTvs = it.getValue(2) as List<Tv>,
                            )
                        }
                    }
            }
            updateState {
                copy(isLoading = false)
            }
        }

        override fun onUiEvent(uiEvent: UiEvent) {
            when (uiEvent) {
                is UiEvent.Navigate -> {
                    sendEffect(Effect.Navigate(uiEvent.router))
                }
            }
        }
    }
