package com.instaleap.favorite.ui.favorite

import com.instaleap.core.route.Router
import com.instaleap.domain.model.Movie
import com.instaleap.domain.model.Tv

interface FavoriteContract {
    data class UiState(
        val isLoading: Boolean = false,
        val movies: List<Movie> = emptyList(),
        val tvs: List<Tv> = emptyList(),
    )

    sealed class UiEvent {
        data class Navigate(
            val router: Router,
        ) : UiEvent()
    }

    sealed class Effect {
        data class Navigate(
            val router: Router,
        ) : Effect()
    }
}
