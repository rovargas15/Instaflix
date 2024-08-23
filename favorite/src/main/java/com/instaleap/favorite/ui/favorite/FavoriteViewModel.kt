package com.instaleap.favorite.ui.favorite

import androidx.lifecycle.viewModelScope
import com.instaleap.core.MviViewModel
import com.instaleap.domain.usecase.GetFavoriteMovie
import com.instaleap.domain.usecase.GetFavoriteTv
import com.instaleap.favorite.ui.favorite.FavoriteContract.Effect
import com.instaleap.favorite.ui.favorite.FavoriteContract.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

@HiltViewModel
class FavoriteViewModel
    @Inject
    constructor(
        private val getFavoriteMovie: GetFavoriteMovie,
        private val getFavoriteTv: GetFavoriteTv,
        private val coroutineDispatcher: CoroutineDispatcher,
    ) : MviViewModel<FavoriteContract.UiState, UiEvent, Effect>() {
        override fun initialState() = FavoriteContract.UiState()

        fun fetchData() {
            viewModelScope.launch(coroutineDispatcher) {
                updateState {
                    copy(isLoading = true)
                }

                // TODO: implement call use case

                updateState {
                    copy(isLoading = false)
                }
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
