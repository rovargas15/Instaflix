package com.instaleap.appkit.component

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.instaleap.appkit.R

@Composable
fun SnackBarError(
    message: String = stringResource(id = R.string.message_error_default),
    state: SnackbarHostState,
    dismissAction: () -> Unit = {},
) {
    LaunchedEffect(state) {
        val result: SnackbarResult =
            state.showSnackbar(
                message = message,
                duration = SnackbarDuration.Long,
                actionLabel = "Ok",
            )
        when (result) {
            SnackbarResult.ActionPerformed -> {
                dismissAction()
            }
            SnackbarResult.Dismissed -> {
                dismissAction()
            }
        }
    }
}
