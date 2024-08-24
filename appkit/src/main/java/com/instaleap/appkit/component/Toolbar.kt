package com.instaleap.appkit.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.instaleap.appkit.R
import com.instaleap.appkit.theme.RedLevel03
import com.instaleap.appkit.theme.paddingMedium
import com.instaleap.core.route.MenuItem
import com.instaleap.core.route.Router

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMovie(
    content: @Composable (PaddingValues) -> Unit,
    router: (Router) -> Unit,
    snackbarHost: @Composable () -> Unit = {},
    selected: Router,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Row(
                        modifier =
                            Modifier.padding(horizontal = paddingMedium),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Logo",
                            modifier = Modifier.fillMaxHeight(),
                            contentScale = ContentScale.Fit,
                        )

                        MenuItem.items.forEach {
                            AppKitLinkButton(
                                text = stringResource(id = it.title),
                                color = if (it.route == selected) RedLevel03 else colorScheme.onPrimaryContainer,
                            ) {
                                router(it.route)
                            }
                        }
                    }
                }
            })
        },
        bottomBar = {
        },
        content = content,
        snackbarHost = {
            snackbarHost()
        },
    )
}
