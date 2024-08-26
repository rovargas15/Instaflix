package com.instaleap.tv.ui.tv

import androidx.lifecycle.viewModelScope
import com.instaleap.core.MviViewModel
import com.instaleap.core.route.Category
import com.instaleap.domain.model.DataBase
import com.instaleap.domain.model.Tv
import com.instaleap.domain.usecase.GetAllTvUseCase
import com.instaleap.domain.usecase.GetTvByCategoryUseCase
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
        private val getTvByCategoryUseCase: GetTvByCategoryUseCase,
        private val getAllTvUseCase: GetAllTvUseCase,
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
                    getTvByCategoryUseCase.invoke(category).fold(
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

        private fun handleError() {
            updateState {
                copy(isError = true)
            }
            viewModelScope.launch(coroutineDispatcher) {
                getAllTvUseCase.invoke().collect {
                    updateState {
                        copy(
                            listPopular = it.filter { it.category == Category.POPULAR },
                            listTopRated = it.filter { it.category == Category.TOP_RATED },
                            listOnTheAir = it.filter { it.category == Category.ON_THE_AIR },
                            isError = false,
                        )
                    }
                }
            }
        }

        private fun handleSuccess(
            category: String,
            dataBase: DataBase<Tv>,
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

                Category.ON_THE_AIR -> {
                    updateState {
                        copy(
                            listOnTheAir = dataBase.results,
                        )
                    }
                }
            }
        }

        override fun onUiEvent(uiEvent: UiEventTv) {
            when (uiEvent) {
                is UiEventTv.Navigate -> {
                    sendEffect(EffectTv.Navigate(uiEvent.router))
                }

                is UiEventTv.Refresh -> {
                    fetchData()
                }
            }
        }
    }
