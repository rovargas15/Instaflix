package com.instaleap.appkit.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextCategory(title: String) {
    Text(
        modifier =
            Modifier.padding(10.dp),
        text = title,
        style = TextStyle().copy(fontSize = 18.sp, fontWeight = FontWeight.SemiBold),
    )
}
