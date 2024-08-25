package com.instaleap.movie.ui.detail

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
import com.instaleap.appkit.theme.paddingXSmall
import com.instaleap.appkit.theme.size120
import com.instaleap.appkit.theme.size150
import com.instaleap.appkit.theme.size20
import com.instaleap.appkit.theme.size350
import com.instaleap.appkit.util.toHour
import com.instaleap.appkit.util.toVote
import com.instaleap.core.CollectEffects
import com.instaleap.domain.model.Movie
import com.instaleap.movie.R
import com.instaleap.movie.ui.detail.DetailContract.EffectDetail
import com.instaleap.movie.ui.detail.DetailContract.UiEventDetail
import com.instaleap.movie.ui.detail.DetailContract.UiStateDetail

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun DetailMovieScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    movieId: Int,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    navigateToBack: () -> Unit,
) {
    HandleState(viewModel, movieId, navigateToBack)

    val uiState by viewModel.uiState.collectAsState()
    ContentMovieDetail(
        uiState = uiState,
        onUiEvent = viewModel::onUiEvent,
        sharedTransitionScope = sharedTransitionScope,
        animatedVisibilityScope = animatedVisibilityScope,
    )
}

@Composable
private fun HandleState(
    viewModel: DetailViewModel,
    movieId: Int,
    navigateToBack: () -> Unit,
) {
    LaunchedEffect(Unit) {
        viewModel.fetchData(movieId)
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
private fun ContentMovieDetail(
    uiState: UiStateDetail,
    onUiEvent: (UiEventDetail) -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    val movie = uiState.movie ?: return
    if (uiState.isShowDialog) {
        ShowDialog(onUiEvent)
    }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(paddingMedium),
    ) {
        ContentHeader(movie, onUiEvent, sharedTransitionScope, animatedVisibilityScope)

        ContentInfoDetail(
            modifier = Modifier.fillMaxWidth(),
            uiState = uiState,
        )

        ContentOverview(uiState.movie)
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
    movie: Movie,
    onUiEvent: (UiEventDetail) -> Unit = {},
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) {
    Box {
        LoaderImage(
            url = movie.backdropPath,
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
                        if (movie.isFavorite) {
                            Icons.Filled.Favorite
                        } else {
                            Icons.Filled.FavoriteBorder
                        },
                    tint = if (movie.isFavorite) Color.Red else Color.Black,
                ) {
                    onUiEvent(UiEventDetail.ToggleFavorite(movie))
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
                                    state = rememberSharedContentState(key = "movie_${movie.category}_${movie.id}"),
                                    animatedVisibilityScope = animatedVisibilityScope,
                                ),
                    ) {
                        LoaderImagePoster(movie.posterPath)
                    }
                }

                ContentInfoMovie(
                    modifier = Modifier.padding(horizontal = paddingMedium).padding(top = size120),
                    uiState = UiStateDetail(movie = movie),
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
    uiState.movieDetail?.let {
        Row(
            modifier = modifier.padding(all = paddingMedium),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                ItemLabelRow(stringResource(R.string.label_runtime))
                ItemRow(it.runtime.toHour())
            }

            Column(modifier = Modifier.weight(1f)) {
                ItemLabelRow(stringResource(R.string.label_release_date))
                ItemRow(it.releaseDate)
            }

            Column(modifier = Modifier.weight(1f)) {
                ItemLabelRow(stringResource(R.string.label_original_language))
                ItemRow(it.originalLanguage)
            }
        }
    }
}

@Composable
private fun ContentInfoMovie(
    modifier: Modifier,
    uiState: UiStateDetail,
) {
    Column(
        modifier = modifier.padding(top = paddingMedium),
    ) {
        Text(
            text = uiState.movie?.title ?: "",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
        )

        Row {
            Icon(
                modifier = Modifier.size(size20),
                imageVector = Icons.Filled.Star,
                contentDescription = "vote",
                tint = Color(0xFFFEB800),
            )
            Text(
                text = uiState.movie?.voteAverage?.toVote() ?: "0",
                modifier = Modifier.align(Alignment.CenterVertically),
                style = MaterialTheme.typography.labelSmall,
            )
        }

        uiState.movieDetail?.genres?.let { genres ->
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
private fun ShowDialog(onUiEvent: (UiEventDetail) -> Unit) {
    ConfirmRemoveFavoriteDialog(
        type = stringResource(id = com.instaleap.appkit.R.string.movie),
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
private fun ContentOverview(movie: Movie) {
    TextCategory(
        stringResource(R.string.label_overview),
        modifier = Modifier.padding(horizontal = paddingMedium),
    )
    Text(
        text = movie.overview,
        style = MaterialTheme.typography.bodySmall,
        modifier = Modifier.padding(horizontal = paddingMedium),
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContentInfoMoviePreview() {
    ContentInfoMovie(
        modifier = Modifier,
        uiState =
            UiStateDetail(
                movie =
                    Movie(
                        id = 1,
                        movieId = 1,
                        adult = false,
                        backdropPath = "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
                        genreIds = listOf(),
                        originalLanguage = "en",
                        title = "Deadpool y Lobezno",
                        overview = "overview",
                        isFavorite = false,
                        voteAverage = 5.0,
                        posterPath = "/9TFSqghEHrlBMRR63yTx80Orxva.jpg",
                        popularity = 5845.013,
                        voteCount = 7761,
                        originalTitle = "Deadpool & Wolverine",
                        video = false,
                        releaseDate = "2024-07-24",
                        category = "",
                    ),
            ),
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContentOverviewPreview() {
    ContentOverview(
        Movie(
            id = 1,
            movieId = 1,
            adult = false,
            backdropPath = "/yDHYTfA3R0jFYba16jBB1ef8oIt.jpg",
            genreIds = listOf(),
            originalLanguage = "en",
            title = "Deadpool y Lobezno",
            overview = "overview",
            isFavorite = false,
            voteAverage = 5.0,
            posterPath = "/9TFSqghEHrlBMRR63yTx80Orxva.jpg",
            popularity = 5845.013,
            voteCount = 7761,
            originalTitle = "Deadpool & Wolverine",
            video = false,
            releaseDate = "2024-07-24",
            category = "",
        ),
    )
}
