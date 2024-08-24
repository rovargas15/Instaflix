package com.instaleap.movie.ui.detail

import com.instaleap.domain.model.Image
import com.instaleap.domain.model.Movie
import com.instaleap.domain.model.MovieDetail

interface DetailContract {
    data class UiStateDetail(
        val isLoading: Boolean = false,
        val error: Boolean = false,
        val movie: Movie? = null,
        val movieDetail: MovieDetail? = null,
        val image: Image? = null,
        val isShowDialog: Boolean = false,
    )

    sealed class UiEventDetail {
        data class ToggleFavorite(
            val movie: Movie,
        ) : UiEventDetail()

        data object NavigateToBack : UiEventDetail()

        data object RemoveFavorite : UiEventDetail()

        data object DismissDialog : UiEventDetail()
    }

    sealed class EffectDetail {
        data object NavigateToBack : EffectDetail()
    }
}
