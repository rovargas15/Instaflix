package com.instaleap.tv.ui.tv

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import com.instaleap.appkit.component.ErrorScreen
import com.instaleap.appkit.component.ItemCard
import com.instaleap.appkit.component.LoaderContent
import com.instaleap.appkit.component.SnackBarError
import com.instaleap.appkit.component.TextCategory
import com.instaleap.appkit.component.TopBarMovie
import com.instaleap.core.CollectEffects
import com.instaleap.core.route.Category
import com.instaleap.core.route.Router
import com.instaleap.domain.model.Tv
import com.instaleap.tv.R
import com.instaleap.tv.ui.tv.TvContract.EffectTv
import com.instaleap.tv.ui.tv.TvContract.UiEventTv

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TvScreen(
    viewModel: TvViewModel = hiltViewModel(),
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigate: (Router) -> Unit,
) {
    HandleEvent(viewModel = viewModel, navigate = navigate)
    val uiState by viewModel.uiState.collectAsState()
    TopBar(
        uiState = uiState,
        onUiEvent = viewModel::onUiEvent,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
    )
}

@Composable
private fun HandleEvent(
    viewModel: TvViewModel,
    navigate: (Router) -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    CollectEffects(viewModel.effects) { effect ->
        when (effect) {
            is EffectTv.Navigate -> {
                navigate(effect.router)
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TopBar(
    uiState: TvContract.UiStateTv,
    onUiEvent: (UiEventTv) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    SnarckBarError(
        uiState = uiState,
        onUiEvent = onUiEvent,
        snackbarHostState = snackbarHostState,
    )
    TopBarMovie(
        selected = Router.Tv,
        content = { innerPadding ->

            if (uiState.isLoading) {
                LoaderContent(modifier = Modifier.padding(innerPadding))
                return@TopBarMovie
            }

            HandleDataEmpty(
                uiState = uiState,
                onUiEvent = onUiEvent,
                innerPadding = innerPadding,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
            )
        },
        router = {
            onUiEvent(UiEventTv.Navigate(it))
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

@Composable
private fun SnarckBarError(
    uiState: TvContract.UiStateTv,
    onUiEvent: (UiEventTv) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    if (uiState.isError) {
        SnackBarError(state = snackbarHostState) {
            onUiEvent(UiEventTv.SnackBarDismissed)
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun HandleDataEmpty(
    uiState: TvContract.UiStateTv,
    onUiEvent: (UiEventTv) -> Unit,
    innerPadding: PaddingValues,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    if (uiState.isLoading) {
        ErrorScreen(
            type = stringResource(id = com.instaleap.appkit.R.string.series),
            errorMessage = "No Data",
        ) {
            onUiEvent(UiEventTv.Refresh)
        }
    } else {
        ContentScreen(
            innerPadding = innerPadding,
            uiState = uiState,
            onUiEvent = onUiEvent,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = animatedVisibilityScope,
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ContentScreen(
    innerPadding: PaddingValues,
    uiState: TvContract.UiStateTv,
    onUiEvent: (UiEventTv) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
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
            TvList(
                tv = uiState.listPopular,
                category = Category.POPULAR,
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
            TvList(
                tv = uiState.listTopRated,
                category = Category.TOP_RATED,
                onUiEvent = onUiEvent,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
            )
        }

        if (uiState.listOnTheAir.isNotEmpty()) {
            TextCategory(
                title = stringResource(R.string.title_on_the_air),
                modifier = Modifier.padding(start = 10.dp),
            )
            TvList(
                tv = uiState.listOnTheAir,
                category = Category.ON_THE_AIR,
                onUiEvent = onUiEvent,
                sharedTransitionScope = sharedTransitionScope,
                animatedVisibilityScope = animatedVisibilityScope,
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TvList(
    tv: List<Tv>,
    category: String,
    onUiEvent: (UiEventTv) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    with(sharedTransitionScope) {
        LazyRow(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            content = {
                items(tv) { tv ->
                    ItemCard(
                        posterPath = tv.posterPath,
                        modifier =
                            Modifier.sharedElement(
                                state = rememberSharedContentState(key = "tv_${category}_${tv.id}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                            ),
                    ) {
                        onUiEvent(UiEventTv.Navigate(Router.DetailTv(tv.id, category)))
                    }
                }
            },
        )
    }
}
