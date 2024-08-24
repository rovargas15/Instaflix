package com.instaleap.core.route

import com.instaleap.core.R
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
        val category: String,
    ) : Router()

    @Serializable
    data object Favorite : Router()
}

data class MenuItem(
    val title: Int,
    val route: Router,
) {
    companion object {
        val items =
            listOf(
                MenuItem(R.string.menu_title_movies, Router.Movie),
                MenuItem(R.string.menu_title_tv_shows, Router.Tv),
                MenuItem(R.string.menu_title_my_list, Router.Favorite),
            )
    }
}

object Category {
    const val POPULAR = "popular"
    const val TOP_RATED = "top_rated"
    const val UPCOMING = "upcoming"
    const val ON_THE_AIR = "airing_today"
}
