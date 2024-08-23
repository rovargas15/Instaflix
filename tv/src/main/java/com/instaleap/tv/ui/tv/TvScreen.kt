package com.instaleap.tv.ui.tv

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

@Composable
fun TvScreen(
    viewModel: TvViewModel = hiltViewModel(),
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
    ContentScreen(uiState, viewModel::onUiEvent)
}

@Composable
fun ContentScreen(
    uiState: TvContract.UiStateTv,
    onUiEvent: (UiEventTv) -> Unit,
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
                    title = stringResource(R.string.title_popular),
                )

                TvList(uiState.tvPopular, onUiEvent)

                TextCategory(
                    title = stringResource(R.string.title_top_rated),
                )

                TvList(uiState.tvTopRated, onUiEvent)

                TextCategory(
                    title = stringResource(R.string.title_on_the_air),
                )

                TvList(uiState.tvOnTheAir, onUiEvent)
            }
        },
        router = {
            onUiEvent(UiEventTv.Navigate(it))
        },
    )
}

@Composable
fun TvList(
    tv: List<Tv>,
    onUiEvent: (UiEventTv) -> Unit,
) {
    LazyRow(
        modifier =
            Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        content = {
            items(tv) { tv ->
                ItemCard(tv.posterPath) {
                    onUiEvent(UiEventTv.Navigate(Router.DetailTv(tv.id)))
                }
            }
        },
    )
}
