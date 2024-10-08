package com.instaleap.movie.ui.movie

import androidx.lifecycle.viewModelScope
import com.instaleap.core.MviViewModel
import com.instaleap.core.route.Category
import com.instaleap.domain.model.DataBase
import com.instaleap.domain.model.Movie
import com.instaleap.domain.usecase.GetAllMovieUseCase
import com.instaleap.domain.usecase.GetMovieByCategoryUseCase
import com.instaleap.movie.ui.movie.MovieContract.EffectMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

@HiltViewModel
class MovieViewModel
    @Inject
    constructor(
        private val getMovieByCategoryUseCase: GetMovieByCategoryUseCase,
        private val getAllMovieUseCase: GetAllMovieUseCase,
        private val coroutineDispatcher: CoroutineDispatcher,
    ) : MviViewModel<MovieContract.UiStateMovie, MovieContract.UiEventMovie, EffectMovie>() {
        private val categories = listOf(Category.POPULAR, Category.TOP_RATED, Category.UPCOMING)

        override fun initialState() = MovieContract.UiStateMovie()

        fun fetchData() {
            if(uiState.value.isEmpty()) {
                viewModelScope.launch(coroutineDispatcher) {
                    updateState {
                        copy(isLoading = true)
                    }
                    categories.forEach { category ->
                        getMovieByCategoryUseCase.invoke(category).fold(
                            onSuccess = {
                                handleSuccess(category, it)
                            },
                            onFailure = {
                                handleError()
                            },
                        )
                    }
                    updateState {
                        copy(isLoading = false)
                    }
                }
            }
        }

        private fun handleSuccess(
            category: String,
            dataBase: DataBase<Movie>,
        ) {
            when (category) {
                Category.POPULAR -> {
                    updateState {
                        copy(
                            listPopular = dataBase.results,
                        )
                    }
                }

                Category.TOP_RATED -> {
                    updateState {
                        copy(
                            listTopRated = dataBase.results,
                        )
                    }
                }

                Category.UPCOMING -> {
                    updateState {
                        copy(
                            listUpcoming = dataBase.results,
                        )
                    }
                }
            }
        }

        private fun handleError() {
            updateState {
                copy(isError = true)
            }

            viewModelScope.launch(coroutineDispatcher) {
                getAllMovieUseCase.invoke().collect {
                    updateState {
                        copy(
                            listPopular = it.filter { it.category == Category.POPULAR },
                            listTopRated = it.filter { it.category == Category.TOP_RATED },
                            listUpcoming = it.filter { it.category == Category.UPCOMING },
                        )
                    }
                }
            }
        }

        override fun onUiEvent(uiEvent: MovieContract.UiEventMovie) {
            when (uiEvent) {
                is MovieContract.UiEventMovie.Navigate -> {
                    sendEffect(EffectMovie.Navigate(uiEvent.router))
                }

                MovieContract.UiEventMovie.Refresh -> {
                    fetchData()
                }

                MovieContract.UiEventMovie.SnackBarDismissed -> {
                    updateState {
                        copy(isError = false)
                    }
                }
            }
        }
    }
