package com.instaleap.appkit.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.instaleap.appkit.theme.paddingSmall
import com.instaleap.appkit.theme.size170
import com.instaleap.appkit.theme.size300

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    posterPath: String,
    action: () -> Unit,
) {
    Card(
        modifier =
            modifier
                .size(size170, size300)
                .padding(start = paddingSmall)
                .clickable {
                    action()
                },
    ) {
        Box {
            LoaderImage(posterPath, Modifier.fillMaxSize())
        }
    }
}
