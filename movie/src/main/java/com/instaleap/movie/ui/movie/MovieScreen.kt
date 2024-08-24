package com.instaleap.movie.ui.movie

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instaleap.appkit.component.ItemCard
import com.instaleap.appkit.component.SnackBarError
import com.instaleap.appkit.component.TextCategory
import com.instaleap.appkit.component.TopBarMovie
import com.instaleap.core.CollectEffects
import com.instaleap.core.route.Router
import com.instaleap.domain.model.Movie
import com.instaleap.movie.R
import com.instaleap.movie.ui.movie.MovieContract.EffectMovie
import com.instaleap.movie.ui.movie.MovieContract.UiEventMovie

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieScreen(
    viewModel: MovieViewModel = hiltViewModel(),
    navigate: (Router) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    HandleEvent(viewModel = viewModel, navigate = navigate)
    val uiState by viewModel.uiState.collectAsState()

    ContentScreen(
        uiState,
        viewModel::onUiEvent,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
    )
}

@Composable
private fun HandleEvent(
    viewModel: MovieViewModel,
    navigate: (Router) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    CollectEffects(viewModel.effects) { effect ->
        when (effect) {
            is EffectMovie.Navigate -> {
                navigate(effect.router)
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ContentScreen(
    uiState: MovieContract.UiStateMovie,
    onUiEvent: (UiEventMovie) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    if (uiState.isError) {
        SnackBarError(state = snackbarHostState)
    }

    TopBarMovie(
        selected = Router.Movie,
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
                if (uiState.listPopular.isNotEmpty()) {
                    TextCategory(
                        title = stringResource(R.string.title_popular),
                        modifier = Modifier.padding(start = 10.dp),
                    )
                    MovieList(
                        movies = uiState.listPopular,
                        onUiEvent = onUiEvent,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                }

                if (uiState.listTopRated.isNotEmpty()) {
                    TextCategory(
                        title = stringResource(R.string.title_top_rated),
                        modifier = Modifier.padding(start = 10.dp),
                    )
                    MovieList(
                        movies = uiState.listTopRated,
                        onUiEvent = onUiEvent,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                }

                if (uiState.listUpcoming.isNotEmpty()) {
                    TextCategory(
                        title = stringResource(R.string.title_upcoming),
                        modifier = Modifier.padding(start = 10.dp),
                    )
                    MovieList(
                        movies = uiState.listUpcoming,
                        onUiEvent = onUiEvent,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                }
            }
        },
        router = {
            onUiEvent(UiEventMovie.Navigate(it))
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(
                    snackbarData = it,
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    actionColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.tertiary,
                )
            }
        },
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MovieList(
    movies: List<Movie>,
    onUiEvent: (UiEventMovie) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    with(sharedTransitionScope) {
        LazyRow(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                items(movies) { movie ->
                    ItemCard(
                        modifier =
                            Modifier.sharedElement(
                                state = rememberSharedContentState(key = "movie_${movie.id}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                            ),
                        posterPath = movie.posterPath,
                    ) {
                        onUiEvent(UiEventMovie.Navigate(Router.DetailMovie(movie.id)))
                    }
                }
            },
        )
    }
}
