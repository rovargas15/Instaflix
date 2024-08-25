package com.instaleap.appkit.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.instaleap.appkit.R
import com.instaleap.appkit.theme.paddingMedium
import com.instaleap.appkit.theme.paddingXMedium

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

@Composable
fun ErrorScreen(
    errorMessage: String,
    type: String,
    onRetry: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(paddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.Message_error_default, type),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Mensaje: $errorMessage", style = MaterialTheme.typography.bodyMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Text(text = stringResource(R.string.btn_try_again))
        }
    }
}
