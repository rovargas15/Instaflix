@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.instaleap.presentation.nav

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
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

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavigatorApp(navController: NavHostController = rememberNavController()) {
    SharedTransitionLayout {
        NavHost(
            startDestination = Router.Movie,
            navController = navController,
        ) {
            movie(
                navController = navController,
                sharedTransitionScope = this@SharedTransitionLayout,
            )
            tv(
                navController = navController,
                sharedTransitionScope = this@SharedTransitionLayout,
            )
            favorite(
                navController = navController,
                sharedTransitionScope = this@SharedTransitionLayout,
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.movie(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
) {
    composable<Router.Movie> {
        MovieScreen(
            navigate = {
                navController.navigate(it)
            },
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = this@composable,
        )
    }

    composable<Router.DetailMovie> {
        DetailMovieScreen(
            movieId = it.toRoute<Router.DetailMovie>().id,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = this@composable,
            navigateToBack = {
                navController.navigateUp()
            },
        )
    }
}

fun NavGraphBuilder.tv(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
) {
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

fun NavGraphBuilder.favorite(
    navController: NavHostController,
    sharedTransitionScope: SharedTransitionScope,
) {
    composable<Router.Favorite> {
        FavoriteScreen(navigate = navController::navigate)
    }
}
