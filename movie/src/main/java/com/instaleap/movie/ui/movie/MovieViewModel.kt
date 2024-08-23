package com.instaleap.movie.ui.movie

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.instaleap.core.MviViewModel
import com.instaleap.domain.usecase.GetMovieByCategory
import com.instaleap.movie.ui.movie.MovieContract.EffectMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel
    @Inject
    constructor(
        private val getMovieByCategory: GetMovieByCategory,
        private val coroutineDispatcher: CoroutineDispatcher,
    ) : MviViewModel<MovieContract.UiStateMovie, MovieContract.UiEventMovie, EffectMovie>() {
        val categories = listOf("popular", "top_rated", "upcoming")

        override fun initialState() = MovieContract.UiStateMovie()

        fun fetchData() {
            viewModelScope.launch(coroutineDispatcher) {
                updateState {
                    copy(isLoading = true)
                }
                categories.forEach { category ->
                    getMovieByCategory.invoke(category).fold(
                        onSuccess = {
                            when (category) {
                                "popular" -> {
                                    updateState {
                                        copy(
                                            moviesPopular = it.results,
                                        )
                                    }
                                }

                                "top_rated" -> {
                                    updateState {
                                        copy(
                                            moviesTopRated = it.results,
                                        )
                                    }
                                }

                                "upcoming" -> {
                                    updateState {
                                        copy(
                                            moviesUpcoming = it.results,
                                        )
                                    }
                                }
                            }
                        },
                        onFailure = {
                            Log.e("MovieViewModel", "getMovies: $it")
                        },
                    )
                }
                updateState {
                    copy(isLoading = false)
                }
            }
        }

        override fun onUiEvent(uiEvent: MovieContract.UiEventMovie) {
            when (uiEvent) {
                is MovieContract.UiEventMovie.Navigate -> {
                    sendEffect(EffectMovie.Navigate(uiEvent.router))
                }
            }
        }
    }
