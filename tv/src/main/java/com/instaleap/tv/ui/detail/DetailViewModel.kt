package com.instaleap.tv.ui.detail

import androidx.lifecycle.viewModelScope
import com.instaleap.core.MviViewModel
import com.instaleap.domain.usecase.GetTvById
import com.instaleap.domain.usecase.GetTvDetailById
import com.instaleap.domain.usecase.UpdateTv
import com.instaleap.tv.ui.detail.DetailContract.EffectDetail
import com.instaleap.tv.ui.detail.DetailContract.UiEventDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel
    @Inject
    constructor(
        private val getDetailUseCase: GetTvDetailById,
        private val getMovieById: GetTvById,
        private val updateTv: UpdateTv,
        private val coroutineDispatcher: CoroutineDispatcher,
    ) : MviViewModel<DetailContract.UiStateDetail, UiEventDetail, EffectDetail>() {
        override fun initialState() = DetailContract.UiStateDetail()

        fun fetchData(movieId: Int) {
            viewModelScope.launch(coroutineDispatcher) {
                getDetailUseCase.invoke(movieId).fold(
                    onSuccess = {
                        updateState {
                            copy(tvDetail = it)
                        }
                    },
                    onFailure = {
                    },
                )
            }
            viewModelScope.launch(coroutineDispatcher) {
                getMovieById.invoke(movieId).collect {
                    updateState {
                        copy(tv = it)
                    }
                }
            }
        }

        private fun update() {
            viewModelScope.launch(coroutineDispatcher) {
                currentUiState.tv?.let {
                    updateTv.invoke(tv = it.copy(isFavorite = !it.isFavorite))
                }
            }
        }

        override fun onUiEvent(uiEvent: UiEventDetail) {
            when (uiEvent) {
                is UiEventDetail.ToggleFavorite -> {
                    update()
                }

                is UiEventDetail.NavigateToBack -> {
                    sendEffect(EffectDetail.NavigateToBack)
                }
            }
        }
    }
