package com.instaleap.tv.ui.tv

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.instaleap.core.MviViewModel
import com.instaleap.domain.usecase.GetTvByCategory
import com.instaleap.tv.ui.tv.TvContract.EffectTv
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

@HiltViewModel
class TvViewModel
    @Inject
    constructor(
        private val getTvByCategory: GetTvByCategory,
        private val coroutineDispatcher: CoroutineDispatcher,
    ) : MviViewModel<TvContract.UiStateTv, TvContract.UiEventTv, EffectTv>() {
        val categories = listOf("popular", "top_rated", "on_the_air")

        override fun initialState() = TvContract.UiStateTv()

        fun fetchData() {
            viewModelScope.launch(coroutineDispatcher) {
                updateState {
                    copy(isLoading = true)
                }
                categories.forEach { category ->
                    getTvByCategory.invoke(category).fold(
                        onSuccess = {
                            when (category) {
                                "popular" -> {
                                    updateState {
                                        copy(
                                            tvPopular = it.results,
                                        )
                                    }
                                }

                                "top_rated" -> {
                                    updateState {
                                        copy(
                                            tvTopRated = it.results,
                                        )
                                    }
                                }

                                "on_the_air" -> {
                                    updateState {
                                        copy(
                                            tvOnTheAir = it.results,
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

        override fun onUiEvent(uiEvent: TvContract.UiEventTv) {
            when (uiEvent) {
                is TvContract.UiEventTv.Navigate -> {
                    sendEffect(EffectTv.Navigate(uiEvent.router))
                }
            }
        }
    }
