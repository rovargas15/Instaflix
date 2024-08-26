package com.instaleap.tv.ui.detail

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.instaleap.appkit.component.ConfirmRemoveFavoriteDialog
import com.instaleap.appkit.component.ContentImage
import com.instaleap.appkit.component.ItemGenre
import com.instaleap.appkit.component.ItemLabelRow
import com.instaleap.appkit.component.ItemRow
import com.instaleap.appkit.component.LoaderImage
import com.instaleap.appkit.component.LoaderImagePoster
import com.instaleap.appkit.component.NavImageIcon
import com.instaleap.appkit.component.TextCategory
import com.instaleap.appkit.theme.paddingMedium
import com.instaleap.appkit.theme.paddingXLarge
import com.instaleap.appkit.theme.paddingXMedium
import com.instaleap.appkit.theme.paddingXSmall
import com.instaleap.appkit.theme.size120
import com.instaleap.appkit.theme.size150
import com.instaleap.appkit.theme.size350
import com.instaleap.appkit.util.toVote
import com.instaleap.core.CollectEffects
import com.instaleap.core.route.Router
import com.instaleap.domain.model.Tv
import com.instaleap.tv.R
import com.instaleap.tv.ui.detail.DetailContract.EffectDetail
import com.instaleap.tv.ui.detail.DetailContract.UiEventDetail
import com.instaleap.tv.ui.detail.DetailContract.UiStateDetail

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailTvScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    tv: Router.DetailTv,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateToBack: () -> Unit,
) {
    HandleState(
        viewModel = viewModel,
        tv = tv,
        navigateToBack = navigateToBack,
    )

    val uiState by viewModel.uiState.collectAsState()

    ContentTvDetail(
        uiState = uiState,
        category = tv.category,
        onUiEvent = viewModel::onUiEvent,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
    )
}

@Composable
private fun HandleState(
    viewModel: DetailViewModel,
    tv: Router.DetailTv,
    navigateToBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.fetchData(tv)
    }

    CollectEffects(viewModel.effects) { effect ->
        when (effect) {
            is EffectDetail.NavigateToBack -> {
                navigateToBack()
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ContentTvDetail(
    uiState: UiStateDetail,
    onUiEvent: (UiEventDetail) -> Unit = {},
    category: String,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val tv = uiState.tv ?: return
    if (uiState.isShowDialog) {
        ShowDialog(onUiEvent, tv)
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(paddingMedium),
    ) {
        ContentHeader(
            tv = tv,
            category = category,
            onUiEvent = onUiEvent,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = animatedVisibilityScope,
        )

        ContentInfoDetail(
            modifier = Modifier.fillMaxWidth(),
            uiState = uiState,
        )

        ContentOverview(uiState.tv)
        uiState.image?.backdrops?.let { posters ->
            if (posters.isNotEmpty()) {
                ContentImage(posters.map { it.filePath })
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun ContentHeader(
    tv: Tv,
    category: String,
    onUiEvent: (UiEventDetail) -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Box {
        LoaderImage(
            url = tv.backdropPath,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(size350),
        )

        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                NavImageIcon(
                    modifier =
                        Modifier
                            .wrapContentSize()
                            .padding(top = paddingXLarge, start = paddingMedium),
                    icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                ) {
                    onUiEvent(UiEventDetail.NavigateToBack)
                }

                NavImageIcon(
                    modifier =
                        Modifier
                            .wrapContentSize()
                            .padding(top = paddingXLarge, end = paddingMedium)
                            .align(alignment = Alignment.CenterVertically),
                    icon =
                        if (tv.isFavorite) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Filled.FavoriteBorder
                        },
                    tint = if (tv.isFavorite) Color.Red else Color.Black,
                ) {
                    onUiEvent(UiEventDetail.ToggleFavorite(tv))
                }
            }

            Spacer(modifier = Modifier.height(size150))

            Row {
                with(sharedTransitionScope) {
                    ElevatedCard(
                        modifier =
                            Modifier
                                .padding(start = paddingMedium)
                                .fillMaxSize(0.4f)
                                .sharedElement(
                                    state = rememberSharedContentState(key = "tv_${category}_${tv.id}"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                ),
                    ) {
                        LoaderImagePoster(tv.posterPath)
                    }
                }

                ContentInfoTv(
                    modifier = Modifier.padding(horizontal = paddingMedium).padding(top = size120),
                    uiState = UiStateDetail(tv = tv),
                )
            }
        }
    }
}

@Composable
private fun ContentInfoDetail(
    modifier: Modifier,
    uiState: UiStateDetail,
) {
    uiState.tvDetail?.let {
        Row(
            modifier = modifier.padding(all = paddingMedium),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                ItemLabelRow(stringResource(R.string.label_first_air_date))
                ItemRow(it.firstAirDate)
            }

            Column(modifier = Modifier.weight(1f)) {
                ItemLabelRow(stringResource(R.string.label_status))
                ItemRow(it.status)
            }

            Column(modifier = Modifier.weight(1f)) {
                ItemLabelRow(stringResource(R.string.label_original_languaje))
                ItemRow(it.originalLanguage)
            }
        }
    }
}

@Composable
private fun ContentInfoTv(
    modifier: Modifier,
    uiState: UiStateDetail,
) {
    Column(
        modifier =
            modifier
                .padding(top = paddingMedium, end = paddingMedium),
    ) {
        Text(
            text = uiState.tv?.name ?: "",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
        )

        Row {
            Icon(
                modifier = Modifier.size(paddingXMedium),
                imageVector = Icons.Filled.Star,
                contentDescription = "vote",
                tint = Color(0xFFFEB800),
            )
            Text(
                text = uiState.tvDetail?.voteAverage?.toVote() ?: "0",
                modifier = Modifier.align(Alignment.CenterVertically),
                style = MaterialTheme.typography.labelSmall,
            )
        }

        uiState.tvDetail?.genres?.let { genres ->
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(paddingXSmall),
            ) {
                items(genres) {
                    ItemGenre(it.name)
                }
            }
        }
    }
}

@Composable
private fun ShowDialog(
    onUiEvent: (UiEventDetail) -> Unit,
    tv: Tv,
) {
    ConfirmRemoveFavoriteDialog(
        type = stringResource(id = com.instaleap.appkit.R.string.movie),
        onDismissRequest = {
            onUiEvent(UiEventDetail.DismissDialog)
        },
        onDismiss = {
            onUiEvent(UiEventDetail.DismissDialog)
        },
        onConfirm = {
            onUiEvent(UiEventDetail.RemoveFavorite(tv))
        },
    )
}

@Composable
fun ContentOverview(tv: Tv) {
    TextCategory(
        stringResource(R.string.title_description),
        modifier = Modifier.padding(horizontal = paddingMedium),
    )
    Text(
        text = tv.overview.ifEmpty { stringResource(R.string.no_overview) },
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(horizontal = paddingMedium),
    )
}

@Preview(showBackground = true)
@Composable
fun ContentTvDetailPreview() {
    val tv =
        Tv(
            adult = false,
            backdropPath = "/suopoADq0k8YZr4dQXcU6pToj6s.jpg",
            firstAirDate = "2011-04-17",
            genreIds = listOf(10765, 18, 80),
            id = 1399,
            name = "Game of Thrones",
            originCountry = listOf("US"),
            originalLanguage = "en",
            originalName = "Game of Thrones",
            overview = "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
            popularity = 266.371,
            posterPath = "/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
            voteAverage = 8.4,
            voteCount = 11798,
            isFavorite = false,
            category = "tv",
        )

    /*ContentOverview(
        tv =
            Tv(
                adult = false,
                backdropPath = "/suopoADq0k8YZr4dQXcU6pToj6s.jpg",
                firstAirDate = "2011-04-17",
                genreIds = listOf(10765, 18, 80),
                id = 1399,
                name = "Game of Thrones",
                originCountry = listOf("US"),
                originalLanguage = "en",
                originalName = "Game of Thrones",
                overview = "Seven noble families fight for control of the mythical land of Westeros. Friction between the houses leads to full-scale war. All while a very ancient evil awakens in the farthest north. Amidst the war, a neglected military order of misfits, the Night's Watch, is all that stands between the realms of men and icy horrors beyond.",
                popularity = 266.371,
                posterPath = "/u3bZgnGQ9T01sWNhyveQz0wH0Hl.jpg",
                voteAverage = 8.4,
                voteCount = 11798,
                isFavorite = false,
                category = "tv",
            ),
    )*/
}
