package com.instaleap.movie.ui.movie

import com.instaleap.core.route.Router
import com.instaleap.domain.model.Movie

interface MovieContract {
    data class UiStateMovie(
        val isLoading: Boolean = false,
        val listPopular: List<Movie> = emptyList(),
        val listTopRated: List<Movie> = emptyList(),
        val listUpcoming: List<Movie> = emptyList(),
        val isError: Boolean = false,
    ) {
        fun isEmpty() = listPopular.isEmpty() && listTopRated.isEmpty() && listUpcoming.isEmpty()
    }

    sealed class UiEventMovie {
        data class Navigate(
            val router: Router,
        ) : UiEventMovie()

        data object Refresh : UiEventMovie()

        data object SnackBarDismissed : UiEventMovie()
    }

    sealed class EffectMovie {
        data class Navigate(
            val router: Router,
        ) : EffectMovie()
    }
}
