package com.instaleap.movie.ui.detail

import androidx.lifecycle.viewModelScope
import com.instaleap.core.MviViewModel
import com.instaleap.domain.usecase.GetImageById
import com.instaleap.domain.usecase.GetMovieById
import com.instaleap.domain.usecase.GetMovieDetailById
import com.instaleap.domain.usecase.UpdateMovie
import com.instaleap.movie.ui.detail.DetailContract.EffectDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
    @Inject
    constructor(
        private val getDetailUseCase: GetMovieDetailById,
        private val getMovieById: GetMovieById,
        private val updateMovie: UpdateMovie,
        private val getImageById: GetImageById,
        private val coroutineDispatcher: CoroutineDispatcher,
    ) : MviViewModel<DetailContract.UiStateDetail, DetailContract.UiEventDetail, EffectDetail>() {
        override fun initialState() = DetailContract.UiStateDetail()

        fun fetchData(movieId: Int) {
            viewModelScope.launch(coroutineDispatcher) {
                getImageById.invoke(movieId, PATH_IMAGE).fold(
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
                getDetailUseCase.invoke(movieId).fold(
                    onSuccess = {
                        updateState {
                            copy(movieDetail = it)
                        }
                    },
                    onFailure = {
                    },
                )
            }
            viewModelScope.launch(coroutineDispatcher) {
                getMovieById.invoke(movieId).collect {
                    updateState {
                        copy(movie = it)
                    }
                }
            }
        }

        private fun updateMovie() {
            viewModelScope.launch(coroutineDispatcher) {
                currentUiState.movie?.let {
                    updateMovie.invoke(movie = it.copy(isFavorite = !it.isFavorite))
                }
            }
        }

        override fun onUiEvent(uiEvent: DetailContract.UiEventDetail) {
            when (uiEvent) {
                is DetailContract.UiEventDetail.ToggleFavorite -> {
                    updateMovie()
                }

                is DetailContract.UiEventDetail.NavigateToBack -> {
                    sendEffect(EffectDetail.NavigateToBack)
                }
            }
        }
    }
private const val PATH_IMAGE = "movie"