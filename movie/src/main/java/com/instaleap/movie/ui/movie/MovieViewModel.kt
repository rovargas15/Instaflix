package com.instaleap.movie.ui.movie

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.instaleap.core.MviViewModel
import com.instaleap.core.route.Category
import com.instaleap.domain.usecase.GetMovieByCategory
import com.instaleap.movie.ui.movie.MovieContract.EffectMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

@HiltViewModel
class MovieViewModel
    @Inject
    constructor(
        private val getMovieByCategory: GetMovieByCategory,
        private val coroutineDispatcher: CoroutineDispatcher,
    ) : MviViewModel<MovieContract.UiStateMovie, MovieContract.UiEventMovie, EffectMovie>() {
        private val categories = listOf(Category.POPULAR, Category.TOP_RATED, Category.UPCOMING)

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
                                Category.POPULAR -> {
                                    updateState {
                                        copy(
                                            listPopular = it.results,
                                        )
                                    }
                                }

                                Category.TOP_RATED -> {
                                    updateState {
                                        copy(
                                            listTopRated = it.results,
                                        )
                                    }
                                }

                                Category.UPCOMING -> {
                                    updateState {
                                        copy(
                                            listUpcoming = it.results,
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
