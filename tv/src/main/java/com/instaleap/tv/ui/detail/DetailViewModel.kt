package com.instaleap.tv.ui.detail

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.instaleap.core.MviViewModel
import com.instaleap.core.route.Router
import com.instaleap.domain.model.Tv
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
            fetchImage(tv.id)
            fetchDetail(tv.id)
            fetchTv(tv.id)
        }

        @VisibleForTesting
        internal fun fetchImage(tvId: Int) {
            viewModelScope.launch(coroutineDispatcher) {
                getImageByIdUseCase.invoke(tvId, PATH_IMAGE).fold(
                    onSuccess = {
                        updateState {
                            copy(image = it)
                        }
                    },
                    onFailure = {
                        updateState {
                            copy(isError = true)
                        }
                    },
                )
            }
        }

        @VisibleForTesting
        internal fun fetchDetail(tvId: Int) {
            viewModelScope.launch(coroutineDispatcher) {
                getDetailUseCase.invoke(tvId).fold(
                    onSuccess = {
                        updateState {
                            copy(tvDetail = it)
                        }
                    },
                    onFailure = {
                        updateState {
                            copy(isError = true)
                        }
                    },
                )
            }
        }

        @VisibleForTesting
        internal fun fetchTv(tvId: Int) {
            viewModelScope.launch(coroutineDispatcher) {
                getMovieById.invoke(tvId).collect {
                    updateState {
                        copy(tv = it)
                    }
                }
            }
        }

        @VisibleForTesting
        internal fun update(tv: Tv) {
            viewModelScope.launch(coroutineDispatcher) {
                updateTvUseCase.invoke(tv = tv.copy(isFavorite = !tv.isFavorite))
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
                        update(uiEvent.tv)
                    }
                }

                is UiEventDetail.NavigateToBack -> {
                    sendEffect(EffectDetail.NavigateToBack)
                }

                is UiEventDetail.DismissDialog -> {
                    updateState {
                        copy(isShowDialog = false)
                    }
                }

                is UiEventDetail.RemoveFavorite -> {
                    update(uiEvent.tv)
                    updateState {
                        copy(isShowDialog = false)
                    }
                }
            }
        }
    }

@VisibleForTesting
internal const val PATH_IMAGE = "tv"
