package com.instaleap.movie.ui.detail

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import com.instaleap.core.MviViewModel
import com.instaleap.domain.model.Movie
import com.instaleap.domain.usecase.GetImageByIdUseCase
import com.instaleap.domain.usecase.GetMovieByIdUseCase
import com.instaleap.domain.usecase.GetMovieDetailByIdUseCase
import com.instaleap.domain.usecase.UpdateMovieUseCase
import com.instaleap.movie.ui.detail.DetailContract.EffectDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel
    @Inject
    constructor(
        private val getDetailUseCase: GetMovieDetailByIdUseCase,
        private val getMovieByIdUseCase: GetMovieByIdUseCase,
        private val updateMovieUseCase: UpdateMovieUseCase,
        private val getImageByIdUseCase: GetImageByIdUseCase,
        private val coroutineDispatcher: CoroutineDispatcher,
    ) : MviViewModel<DetailContract.UiStateDetail, DetailContract.UiEventDetail, EffectDetail>() {
        override fun initialState() = DetailContract.UiStateDetail()

        fun fetchData(movieId: Int) {
            fetchImage(movieId)
            fetchDetail(movieId)
            fetchMovie(movieId)
        }

        @VisibleForTesting
        internal fun fetchImage(movieId: Int) {
            viewModelScope.launch(coroutineDispatcher) {
                getImageByIdUseCase.invoke(movieId, PATH_IMAGE).fold(
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
        internal fun fetchDetail(movieId: Int) {
            viewModelScope.launch(coroutineDispatcher) {
                getDetailUseCase.invoke(movieId).fold(
                    onSuccess = {
                        updateState {
                            copy(movieDetail = it)
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
        internal fun fetchMovie(movieId: Int) {
            viewModelScope.launch(coroutineDispatcher) {
                getMovieByIdUseCase.invoke(movieId).collect {
                    updateState {
                        copy(movie = it)
                    }
                }
            }
        }

        @VisibleForTesting
        internal fun updateMovie(movie: Movie) {
            viewModelScope.launch(coroutineDispatcher) {
                updateMovieUseCase.invoke(movie = movie.copy(isFavorite = !movie.isFavorite))
            }
        }

        override fun onUiEvent(uiEvent: DetailContract.UiEventDetail) {
            when (uiEvent) {
                is DetailContract.UiEventDetail.ToggleFavorite -> {
                    if (uiEvent.movie.isFavorite) {
                        updateState {
                            copy(isShowDialog = true)
                        }
                    } else {
                        updateMovie(uiEvent.movie)
                    }
                }

                is DetailContract.UiEventDetail.NavigateToBack -> {
                    sendEffect(EffectDetail.NavigateToBack)
                }

                is DetailContract.UiEventDetail.RemoveFavorite -> {
                    updateMovie(uiEvent.movie)
                    updateState {
                        copy(isShowDialog = false)
                    }
                }

                else -> {
                    updateState {
                        copy(isShowDialog = false)
                    }
                }
            }
        }
    }

@VisibleForTesting
internal const val PATH_IMAGE = "movie"
