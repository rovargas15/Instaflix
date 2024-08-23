package com.instaleap.appkit.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.instaleap.data.BuildConfig

@Composable
fun LoaderImage(
    url: String,
    modifier: Modifier,
) {
    AsyncImage(
        model = "${BuildConfig.URL_BASE_IMG}original/$url",
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun LoaderImagePoster(
    url: String,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = "${BuildConfig.URL_BASE_IMG}w500/$url",
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Inside,
    )
}
