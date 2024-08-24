package com.instaleap.appkit.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.instaleap.appkit.theme.size35

@Composable
fun NavImageIcon(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = Color.Unspecified,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            modifier =
                Modifier
                    .size(size35)
                    .background(Color.LightGray.copy(0.2f), shape = CircleShape),
            imageVector = icon,
            tint = tint,
            contentDescription = "Volver",
        )
    }
}
