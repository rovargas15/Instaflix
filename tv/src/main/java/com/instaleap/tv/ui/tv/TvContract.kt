package com.instaleap.tv.ui.tv

import com.instaleap.core.route.Router
import com.instaleap.domain.model.Tv

interface TvContract {
    data class UiStateTv(
        val isLoading: Boolean = false,
        val listPopular: List<Tv> = emptyList(),
        val listTopRated: List<Tv> = emptyList(),
        val listOnTheAir: List<Tv> = emptyList(),
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
