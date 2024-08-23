package com.instaleap.tv.ui.detail

import com.instaleap.domain.model.Tv
import com.instaleap.domain.model.TvDetail
import com.instaleap.domain.model.Image

interface DetailContract {
    data class UiStateDetail(
        val isLoading: Boolean = false,
        val error: Boolean = false,
        val tv: Tv? = null,
        val tvDetail: TvDetail? = null,
        val image: Image? = null,
    )

    sealed class UiEventDetail {
        data class ToggleFavorite(
            val tv: Tv,
        ) : UiEventDetail()

        data object NavigateToBack : UiEventDetail()
    }

    sealed class EffectDetail {
        data object NavigateToBack : EffectDetail()
    }
}
