package com.instaleap.tv.ui.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.instaleap.appkit.component.TextCategory
import com.instaleap.appkit.component.TopBarMovie

@Composable
fun DetailTvScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    movieId: Int,
    onBackPressed: () -> Unit,
) {
    TopBarMovie(
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                TextCategory(title = "Detail tv $movieId")
            }
        },
        router = {
        },
    )
}
