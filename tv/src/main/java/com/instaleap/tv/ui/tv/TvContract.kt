package com.instaleap.tv.ui.tv

import com.instaleap.core.route.Router
import com.instaleap.domain.model.Tv

interface TvContract {
    data class UiStateTv(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val listPopular: List<Tv> = emptyList(),
        val listTopRated: List<Tv> = emptyList(),
        val listOnTheAir: List<Tv> = emptyList(),
    ) {
        fun isEmpty() = listPopular.isEmpty() && listTopRated.isEmpty() && listOnTheAir.isEmpty()
    }

    sealed class UiEventTv {
        data class Navigate(
            val router: Router,
        ) : UiEventTv()

        data object Refresh : UiEventTv()

        data object SnackBarDismissed : UiEventTv()
    }

    sealed class EffectTv {
        data class Navigate(
            val router: Router,
        ) : EffectTv()
    }
}
