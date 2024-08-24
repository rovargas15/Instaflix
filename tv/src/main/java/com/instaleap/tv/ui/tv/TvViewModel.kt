package com.instaleap.tv.ui.tv

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.instaleap.core.MviViewModel
import com.instaleap.core.route.Category
import com.instaleap.domain.usecase.GetTvByCategory
import com.instaleap.tv.ui.tv.TvContract.EffectTv
import com.instaleap.tv.ui.tv.TvContract.UiEventTv
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvViewModel
    @Inject
    constructor(
        private val getTvByCategory: GetTvByCategory,
        private val coroutineDispatcher: CoroutineDispatcher,
    ) : MviViewModel<TvContract.UiStateTv, UiEventTv, EffectTv>() {
        private val categories = listOf(Category.POPULAR, Category.TOP_RATED, Category.ON_THE_AIR)

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

                                Category.ON_THE_AIR -> {
                                    updateState {
                                        copy(
                                            listOnTheAir = it.results,
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

        override fun onUiEvent(uiEvent: UiEventTv) {
            when (uiEvent) {
                is UiEventTv.Navigate -> {
                    sendEffect(EffectTv.Navigate(uiEvent.router))
                }
            }
        }
    }
