package com.instaleap.favorite.ui.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instaleap.appkit.component.ItemCard
import com.instaleap.appkit.component.TextCategory
import com.instaleap.appkit.component.TopBarMovie
import com.instaleap.core.CollectEffects
import com.instaleap.core.route.Router
import com.instaleap.domain.model.Movie
import com.instaleap.domain.model.Tv
import com.instaleap.favorite.ui.favorite.FavoriteContract.UiEvent

@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    navigate: (Router) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    CollectEffects(viewModel.effects) { effect ->
        when (effect) {
            is FavoriteContract.Effect.Navigate -> {
                navigate(effect.router)
            }
        }
    }
    val uiState by viewModel.uiState.collectAsState()
    ContentScreen(uiState, viewModel::onUiEvent)
}

@Composable
fun ContentScreen(
    uiState: FavoriteContract.UiState,
    onUiEvent: (UiEvent) -> Unit,
) {
    TopBarMovie(
        content = { innerPadding ->

            if (uiState.isLoading) {
                // TODO: implement loader here
            }

            Column(
                modifier =
                    Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                TextCategory(
                    title = "Movie",
                )

                MovieList(uiState.movies, onUiEvent)

                TextCategory(
                    title = "Tv Show",
                )

                TvList(uiState.tvs, onUiEvent)
            }
        },
        router = {
            onUiEvent(UiEvent.Navigate(it))
        },
    )
}

@Composable
fun MovieList(
    movies: List<Movie>,
    onUiEvent: (UiEvent) -> Unit,
) {
    LazyRow(
        modifier =
            Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(movies) { movie ->
                ItemCard(movie.posterPath) {
                    onUiEvent(UiEvent.Navigate(Router.DetailMovie(movie.id)))
                }
            }
        },
    )
}

@Composable
fun TvList(
    tv: List<Tv>,
    onUiEvent: (UiEvent) -> Unit,
) {
    LazyRow(
        modifier =
            Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(tv) { tv ->
                ItemCard(tv.posterPath) {
                    onUiEvent(UiEvent.Navigate(Router.DetailTv(tv.id)))
                }
            }
        },
    )
}
