package com.instaleap.core.route

import kotlinx.serialization.Serializable

@Serializable
sealed class Router {
    @Serializable
    data object Movie : Router()

    @Serializable
    data class DetailMovie(
        val id: Int,
    ) : Router()

    @Serializable
    data object Tv : Router()

    @Serializable
    data class DetailTv(
        val id: Int,
    ) : Router()

    @Serializable
    data object Favorite : Router()
}

object CategoryMovie {
    const val POPULAR = "popular"
    const val TOP_RATED = "top_rated"
    const val UPCOMING = "upcoming"
}

object CategoryTv {
    const val POPULAR = "popular"
    const val TOP_RATED = "top_rated"
    const val ON_THE_AIR = "on_the_air"
}
