package com.instaleap.appkit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.instaleap.appkit.R
import com.instaleap.appkit.theme.paddingMedium
import com.instaleap.appkit.theme.paddingSmall
import com.instaleap.appkit.theme.paddingXMedium
import com.instaleap.appkit.theme.paddingXSmall

@Composable
fun ContentImage(images: List<String>?) {
    images?.let {
        TextCategory(
            stringResource(R.string.label_image),
            modifier = Modifier.padding(horizontal = paddingMedium),
        )

        LazyRow(
            modifier =
                Modifier
                    .padding(bottom = paddingXMedium)
                    .padding(horizontal = paddingMedium),
            horizontalArrangement = Arrangement.spacedBy(paddingMedium),
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
