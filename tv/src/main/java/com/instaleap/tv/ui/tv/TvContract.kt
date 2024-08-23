package com.instaleap.tv.ui.tv

import com.instaleap.core.route.Router
import com.instaleap.domain.model.Tv

interface TvContract {
    data class UiStateTv(
        val isLoading: Boolean = false,
        val tvPopular: List<Tv> = emptyList(),
        val tvTopRated: List<Tv> = emptyList(),
        val tvOnTheAir: List<Tv> = emptyList(),
    )

    sealed class UiEventTv {
        data class Navigate(
            val router: Router,
        ) : UiEventTv()
    }

    sealed class EffectTv {
        data class Navigate(
            val router: Router,
        ) : EffectTv()
    }
}
