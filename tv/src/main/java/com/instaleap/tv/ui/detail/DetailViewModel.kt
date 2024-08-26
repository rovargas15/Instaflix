package com.instaleap.tv.ui.detail

import androidx.lifecycle.viewModelScope
import com.instaleap.core.MviViewModel
import com.instaleap.core.route.Router
import com.instaleap.domain.usecase.GetImageByIdUseCase
import com.instaleap.domain.usecase.GetTvByIdUseCase
import com.instaleap.domain.usecase.GetTvDetailByIdUseCase
import com.instaleap.domain.usecase.UpdateTvUseCase
import com.instaleap.tv.ui.detail.DetailContract.EffectDetail
import com.instaleap.tv.ui.detail.DetailContract.UiEventDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
    @Inject
    constructor(
        private val getDetailUseCase: GetTvDetailByIdUseCase,
        private val getMovieById: GetTvByIdUseCase,
        private val updateTvUseCase: UpdateTvUseCase,
        private val getImageByIdUseCase: GetImageByIdUseCase,
        private val coroutineDispatcher: CoroutineDispatcher,
    ) : MviViewModel<DetailContract.UiStateDetail, UiEventDetail, EffectDetail>() {
        override fun initialState() = DetailContract.UiStateDetail()

        fun fetchData(tv: Router.DetailTv) {
            viewModelScope.launch(coroutineDispatcher) {
                getImageByIdUseCase.invoke(tv.id, PATH_IMAGE).fold(
                    onSuccess = {
                        updateState {
                            copy(image = it)
                        }
                    },
                    onFailure = {
                    },
                )
            }
            viewModelScope.launch(coroutineDispatcher) {
                getDetailUseCase.invoke(tv.id).fold(
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
                getMovieById.invoke(tv.id).collect {
                    updateState {
                        copy(tv = it)
                    }
                }
            }
        }

        private fun update() {
            viewModelScope.launch(coroutineDispatcher) {
                currentUiState.tv?.let {
                    updateTvUseCase.invoke(tv = it.copy(isFavorite = !it.isFavorite))
                }
            }
        }

        override fun onUiEvent(uiEvent: UiEventDetail) {
            when (uiEvent) {
                is UiEventDetail.ToggleFavorite -> {
                    if (uiEvent.tv.isFavorite) {
                        updateState {
                            copy(isShowDialog = true)
                        }
                    } else {
                        update()
                    }
                }

                is UiEventDetail.NavigateToBack -> {
                    sendEffect(EffectDetail.NavigateToBack)
                }

                UiEventDetail.DismissDialog -> {
                    updateState {
                        copy(isShowDialog = false)
                    }
                }

                UiEventDetail.RemoveFavorite -> {
                    update()
                    updateState {
                        copy(isShowDialog = false)
                    }
                }
            }
        }
    }

private const val PATH_IMAGE = "tv"
