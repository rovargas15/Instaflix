package com.instaleap.appkit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.instaleap.appkit.theme.paddingSmall
import com.instaleap.appkit.theme.paddingXSmall

@Composable
fun TextCategory(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier.padding(top = paddingSmall, bottom = paddingSmall),
        text = title,
        style = TextStyle().copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
    )
}

@Composable
fun ItemLabelRow(text: String) {
    Text(
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        text = text,
        style = MaterialTheme.typography.labelSmall,
    )
}

@Composable
fun ItemRow(text: String) {
    Text(
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth(),
        text = text,
        style = MaterialTheme.typography.bodyLarge,
    )
}

@Composable
fun ItemGenre(genre: String) {
    Box(
        modifier = Modifier.background(Color.Blue.copy(alpha = .1f), shape = CircleShape),
    ) {
        Text(
            text = genre,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(paddingXSmall),
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemLabelRowPreview() {
    ItemLabelRow(text = "Accion")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemRowPreview() {
    ItemRow(text = "Accion")
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun itemGenrePreview() {
    ItemGenre(genre = "Accion")
}
