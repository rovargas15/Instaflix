package com.instaleap.movie.ui.movie

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.instaleap.core.MviViewModel
import com.instaleap.core.route.Category
import com.instaleap.domain.model.DataBase
import com.instaleap.domain.model.Movie
import com.instaleap.domain.usecase.GetAllMovie
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
        private val getAllMovie: GetAllMovie,
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
                            handleSuccess(category, it)
                        },
                        onFailure = {
                            Log.e("MovieViewModel", "getMovies: $it")
                            handleError()
                        },
                    )
                }
                updateState {
                    copy(isLoading = false)
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
                getAllMovie.invoke().collect {
                    updateState {
                        copy(
                            listPopular = it.filter { it.category == Category.POPULAR },
                            listTopRated = it.filter { it.category == Category.TOP_RATED },
                            listUpcoming = it.filter { it.category == Category.UPCOMING },
                            isError = false,
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
            }
        }
    }
