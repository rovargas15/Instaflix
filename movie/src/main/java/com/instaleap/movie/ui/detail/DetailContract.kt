package com.instaleap.movie.ui.detail

import com.instaleap.domain.model.Movie
import com.instaleap.domain.model.MovieDetail
import com.instaleap.domain.model.Image

interface DetailContract {
    data class UiStateDetail(
        val isLoading: Boolean = false,
        val error: Boolean = false,
        val movie: Movie? = null,
        val movieDetail: MovieDetail? = null,
        val image: Image? = null,
    )

    sealed class UiEventDetail {
        data class ToggleFavorite(
            val movie: Movie,
        ) : UiEventDetail()

        data object NavigateToBack : UiEventDetail()
    }

    sealed class EffectDetail {
        data object NavigateToBack : EffectDetail()
    }
}
