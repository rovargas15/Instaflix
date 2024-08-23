package com.instaleap.tv.ui.detail

interface DetailContract {

    data class UiStateDetail(
        val loading: Boolean = false,
        val error: Boolean = false,
    )

    sealed class UiEventDetail {
        data class Favorite(val isFavorite: Boolean) : UiEventDetail()
    }

    sealed class EffectDetail {
        data object NavigateToDetail : EffectDetail()
        data object NavigateToBack : EffectDetail()
    }
}