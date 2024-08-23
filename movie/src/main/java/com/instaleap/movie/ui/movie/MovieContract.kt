package com.instaleap.movie.ui.movie

import com.instaleap.core.route.Router
import com.instaleap.domain.model.Movie

interface MovieContract {
    data class UiStateMovie(
        val isLoading: Boolean = false,
        val moviesPopular: List<Movie> = emptyList(),
        val moviesTopRated: List<Movie> = emptyList(),
        val moviesUpcoming: List<Movie> = emptyList(),
    )

    sealed class UiEventMovie {
        data class Navigate(
            val router: Router,
        ) : UiEventMovie()
    }

    sealed class EffectMovie {
        data class Navigate(
            val router: Router,
        ) : EffectMovie()
    }
}
