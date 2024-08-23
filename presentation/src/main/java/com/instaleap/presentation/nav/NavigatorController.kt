package com.instaleap.presentation.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.instaleap.core.route.Router
import com.instaleap.favorite.ui.favorite.FavoriteScreen
import com.instaleap.movie.ui.detail.DetailMovieScreen
import com.instaleap.movie.ui.movie.MovieScreen
import com.instaleap.tv.ui.detail.DetailTvScreen
import com.instaleap.tv.ui.tv.TvScreen

@Composable
fun NavigatorApp(navController: NavHostController = rememberNavController()) {
    NavHost(
        startDestination = Router.Movie,
        navController = navController,
    ) {
        movie(navController)
        tv(navController)
        favorite(navController)
    }
}

fun NavGraphBuilder.movie(navController: NavHostController) {
    composable<Router.Movie> {
        MovieScreen(navigate = navController::navigate)
    }

    composable<Router.DetailMovie> {
        DetailMovieScreen(
            movieId = it.toRoute<Router.DetailMovie>().id,
            navigateToBack = {
                navController.navigateUp()
            },
        )
    }
}

fun NavGraphBuilder.tv(navController: NavHostController) {
    composable<Router.Tv> {
        TvScreen(navigate = navController::navigate)
    }

    composable<Router.DetailTv> {
        DetailTvScreen(
            tvId = it.toRoute<Router.DetailTv>().id,
            navigateToBack = {
                navController.navigateUp()
            },
        )
    }
}

fun NavGraphBuilder.favorite(navController: NavHostController) {
    composable<Router.Favorite> {
        FavoriteScreen(navigate = navController::navigate)
    }
}
