package com.instaleap.favorite.ui.favorite

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instaleap.appkit.component.ItemCard
import com.instaleap.appkit.component.TextCategory
import com.instaleap.appkit.component.TopBarMovie
import com.instaleap.core.CollectEffects
import com.instaleap.core.route.Router
import com.instaleap.domain.model.Movie
import com.instaleap.domain.model.Tv
import com.instaleap.favorite.R
import com.instaleap.favorite.ui.favorite.FavoriteContract.UiEvent

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun FavoriteScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    navigate: (Router) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
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
    ContentScreen(
        uiState = uiState,
        onUiEvent = viewModel::onUiEvent,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ContentScreen(
    uiState: FavoriteContract.UiState,
    onUiEvent: (UiEvent) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    TopBarMovie(
        selected = Router.Favorite,
        content = { innerPadding ->
            if (uiState.isListEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    EmptyFavoritesScreen()
                }
            } else {
                Column(
                    modifier =
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    if (uiState.listMovies.isNotEmpty()) {
                        TextCategory(
                            title = stringResource(R.string.title_movie),
                            modifier = Modifier.padding(start = 10.dp),
                        )
                        MovieList(
                            movies = uiState.listMovies,
                            onUiEvent = onUiEvent,
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                    }

                    if (uiState.listTvs.isNotEmpty()) {
                        TextCategory(
                            title = stringResource(R.string.title_tv),
                            modifier = Modifier.padding(start = 10.dp),
                        )
                        TvList(
                            tv = uiState.listTvs,
                            onUiEvent = onUiEvent,
                            sharedTransitionScope = sharedTransitionScope,
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                    }
                }
            }
        },
        router = {
            onUiEvent(UiEvent.Navigate(it))
        },
    )
}

@Composable
fun EmptyFavoritesScreen() {
    Column(
        modifier =
            Modifier
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.title_msg_empty),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 16.dp),
        )

        Text(
            text = stringResource(R.string.label_instructional_text),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp),
        )

        Text(
            text = stringResource(R.string.label_How_to_message),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        Text(
            text = stringResource(R.string.message_instructions_favorite),
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieList(
    movies: List<Movie>,
    onUiEvent: (UiEvent) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    with(sharedTransitionScope) {
        LazyRow(
            modifier =
                Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                items(movies) { movie ->
                    ItemCard(
                        posterPath = movie.posterPath,
                        modifier =
                            Modifier.sharedElement(
                                state = rememberSharedContentState(key = "movie_${movie.id}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                            ),
                    ) {
                        onUiEvent(UiEvent.Navigate(Router.DetailMovie(movie.id)))
                    }
                }
            },
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TvList(
    tv: List<Tv>,
    onUiEvent: (UiEvent) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    with(sharedTransitionScope) {
        LazyRow(
            modifier =
                Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                items(tv) { tv ->
                    ItemCard(
                        posterPath = tv.posterPath,
                        modifier =
                            Modifier.sharedElement(
                                state = rememberSharedContentState(key = "tv_${tv.id}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                            ),
                    ) {
                        onUiEvent(UiEvent.Navigate(Router.DetailTv(tv.id, "")))
                    }
                }
            },
        )
    }
}
