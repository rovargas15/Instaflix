package com.instaleap.appkit.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.instaleap.appkit.R
import com.instaleap.data.BuildConfig

@Composable
fun LoaderImage(
    url: String,
    modifier: Modifier,
) {
    val imageRequest =
        ImageRequest
            .Builder(context = LocalContext.current)
            .data("${BuildConfig.URL_BASE_IMG}original/$url")
            .memoryCacheKey("${BuildConfig.URL_BASE_IMG}original/$url")
            .diskCacheKey("${BuildConfig.URL_BASE_IMG}original/$url")
            .error(R.drawable.ic_no_image)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .placeholderMemoryCacheKey(MemoryCache.Key("${BuildConfig.URL_BASE_IMG}original/$url"))
            .build()

    AsyncImage(
        model = imageRequest,
        contentDescription = null,
        modifier = modifier.fillMaxSize(),
        contentScale = ContentScale.Crop,
    )
}

@Composable
fun LoaderImagePoster(
    url: String,
    modifier: Modifier = Modifier,
) {
    val imageRequest =
        ImageRequest
            .Builder(context = LocalContext.current)
            .data("${BuildConfig.URL_BASE_IMG}w500/$url")
            .memoryCacheKey("${BuildConfig.URL_BASE_IMG}w500/$url")
            .diskCacheKey("${BuildConfig.URL_BASE_IMG}w500/$url")
            .error(R.drawable.ic_no_image)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .placeholderMemoryCacheKey(MemoryCache.Key("${BuildConfig.URL_BASE_IMG}w500/$url"))
            .build()

    AsyncImage(
        model = imageRequest,
        modifier = modifier,
        contentDescription = null,
        contentScale = ContentScale.Fit,
    )
}
