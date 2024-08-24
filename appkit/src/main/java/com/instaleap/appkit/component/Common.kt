package com.instaleap.appkit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.instaleap.appkit.R

@Composable
fun ContentImage(images: List<String>?) {
    images?.let {
        Text(
            stringResource(R.string.label_image),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        LazyRow(
            modifier = Modifier.padding(bottom = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(it) { image ->
                ElevatedCard {
                    LoaderImagePoster(
                        url = image,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}
