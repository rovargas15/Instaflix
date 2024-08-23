package com.instaleap.movie.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.instaleap.appkit.component.TextCategory
import com.instaleap.appkit.component.TopBarMovie
import com.instaleap.presentation.movie.detail.DetailViewModel

@Composable
fun DetailMovieScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    movieId: Int,
    onBackPressed: () -> Unit,
) {
    TopBarMovie(
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                TextCategory(title = "Detail Movie $movieId")
            }
        },
        router = {},
    )
}
