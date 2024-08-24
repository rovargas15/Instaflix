package com.instaleap.tv.ui.tv

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.instaleap.appkit.component.ItemCard
import com.instaleap.appkit.component.TextCategory
import com.instaleap.appkit.component.TopBarMovie
import com.instaleap.core.CollectEffects
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
    uiState: TvContract.UiStateTv,
    onUiEvent: (UiEventTv) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    TopBarMovie(
        selected = Router.Tv,
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
                    TvList(
                        tv = uiState.listPopular,
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
                        onUiEvent = onUiEvent,
                        sharedTransitionScope = sharedTransitionScope,
                        animatedVisibilityScope = animatedVisibilityScope,
                    )
                }
            }
        },
        router = {
            onUiEvent(UiEventTv.Navigate(it))
        },
    )
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun TvList(
    tv: List<Tv>,
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
                                state = rememberSharedContentState(key = "tv_${tv.id}"),
                                animatedVisibilityScope = animatedVisibilityScope,
                            ),
                    ) {
                        onUiEvent(UiEventTv.Navigate(Router.DetailTv(tv.id)))
                    }
                }
            },
        )
    }
}
