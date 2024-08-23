package com.instaleap.appkit.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ItemCard(
    posterPath: String,
    action: () -> Unit,
) {
    Card(
        modifier = Modifier
            .size(170.dp, 300.dp)
            .padding(start = 10.dp)
            .clickable {
                action()
            },
    ) {
        Box {
            LoaderImage(posterPath, Modifier.fillMaxSize())
        }
    }
}