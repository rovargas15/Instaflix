package com.instaleap.tv.ui.detail

import com.instaleap.domain.model.Image
import com.instaleap.domain.model.Tv
import com.instaleap.domain.model.TvDetail

interface DetailContract {
    data class UiStateDetail(
        val isLoading: Boolean = false,
        val isError: Boolean = false,
        val tv: Tv? = null,
        val tvDetail: TvDetail? = null,
        val image: Image? = null,
        val isShowDialog: Boolean = false,
    )

    sealed class UiEventDetail {
        data class ToggleFavorite(
            val tv: Tv,
        ) : UiEventDetail()

        data object NavigateToBack : UiEventDetail()

        data class RemoveFavorite(
            val tv: Tv,
        ) : UiEventDetail()

        data object DismissDialog : UiEventDetail()
    }

    sealed class EffectDetail {
        data object NavigateToBack : EffectDetail()
    }
}
