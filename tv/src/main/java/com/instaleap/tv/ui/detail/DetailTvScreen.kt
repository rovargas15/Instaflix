package com.instaleap.tv.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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
import com.instaleap.core.CollectEffects
import com.instaleap.domain.model.Tv
import com.instaleap.tv.R
import com.instaleap.tv.ui.detail.DetailContract.EffectDetail
import com.instaleap.tv.ui.detail.DetailContract.UiEventDetail
import com.instaleap.tv.ui.detail.DetailContract.UiStateDetail

@Composable
fun DetailTvScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    tvId: Int,
    navigateToBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.fetchData(tvId)
    }

    CollectEffects(viewModel.effects) { effect ->
        when (effect) {
            is EffectDetail.NavigateToBack -> {
                navigateToBack()
            }
        }
    }

    val uiState by viewModel.uiState.collectAsState()
    ContentTvDetail(uiState, viewModel::onUiEvent)
}

@Composable
private fun ContentTvDetail(
    uiState: UiStateDetail,
    onUiEvent: (UiEventDetail) -> Unit = {},
) {
    val tv = uiState.tv ?: return
    if (uiState.isShowDialog) {
        ShowDialog(onUiEvent)
    }

    ConstraintLayout(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
    ) {
        val (header, btnBack, btnFavorite, poster, infoTv, infoDetail, overview) = createRefs()

        LoaderImage(
            url = tv.backdropPath,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .constrainAs(header) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
        )

        NavImageIcon(
            modifier =
                Modifier
                    .padding(16.dp)
                    .constrainAs(btnBack) {
                        top.linkTo(header.top)
                        start.linkTo(parent.start)
                    },
            icon = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
        ) {
            onUiEvent(UiEventDetail.NavigateToBack)
        }

        NavImageIcon(
            modifier =
                Modifier
                    .padding(16.dp)
                    .constrainAs(btnFavorite) {
                        top.linkTo(header.top)
                        end.linkTo(parent.end)
                    },
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

        ElevatedCard(
            modifier =
                Modifier
                    .padding(start = 16.dp)
                    .constrainAs(poster) {
                        top.linkTo(header.bottom)
                        bottom.linkTo(header.bottom)
                        start.linkTo(parent.start)
                    },
        ) {
            LoaderImagePoster(tv.posterPath)
        }

        Column(
            modifier =
                Modifier
                    .padding(all = 16.dp)
                    .constrainAs(infoTv) {
                        top.linkTo(header.bottom)
                        bottom.linkTo(poster.bottom)
                        start.linkTo(poster.end)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
        ) {
            Text(
                text = tv.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold,
            )

            Row {
                Icon(
                    modifier = Modifier.size(20.dp),
                    imageVector = Icons.Filled.Star,
                    contentDescription = "vote",
                    tint = Color(0xFFFEB800),
                )
                Text(
                    text = tv.voteAverage.toString(),
                    modifier = Modifier.align(Alignment.CenterVertically),
                    style = MaterialTheme.typography.labelSmall,
                )
            }

            uiState.tvDetail?.genres?.let { genres ->
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(genres) {
                        ItemGenre(it.name)
                    }
                }
            }
        }

        Row(
            modifier =
                Modifier
                    .padding(all = 16.dp)
                    .constrainAs(infoDetail) {
                        top.linkTo(poster.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
        ) {
            uiState.tvDetail?.let {
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

        Column(
            modifier =
                Modifier
                    .padding(horizontal = 16.dp)
                    .constrainAs(overview) {
                        top.linkTo(infoDetail.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    },
        ) {
            ContentOverview(uiState.tv)
            uiState.image?.backdrops?.let {
                if (it.isNotEmpty()) {
                    ContentImage(it.map { it.filePath })
                }
            }
        }
    }
}

@Composable
private fun ShowDialog(onUiEvent: (UiEventDetail) -> Unit) {
    ConfirmRemoveFavoriteDialog(
        onDismissRequest = {
            onUiEvent(UiEventDetail.DismissDialog)
        },
        onDismiss = {
            onUiEvent(UiEventDetail.DismissDialog)
        },
        onConfirm = {
            onUiEvent(UiEventDetail.RemoveFavorite)
        },
    )
}

@Composable
fun ContentOverview(tv: Tv) {
    TextCategory(
        stringResource(R.string.title_description),
    )
    Text(
        text = tv.overview.ifEmpty { stringResource(R.string.no_overview) },
        style = MaterialTheme.typography.bodySmall,
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContentTvDetailPreview() {
    ContentTvDetail(
        uiState =
            UiStateDetail(
                isLoading = false,
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
                    ),
                image = null,
            ),
    )
}
