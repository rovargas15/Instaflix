package com.instaleap.appkit.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.instaleap.appkit.R
import com.instaleap.core.route.Router

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMovie(
    content: @Composable (PaddingValues) -> Unit,
    router: (Router) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Row(
                    modifier =
                        Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo",
                        modifier = Modifier.size(40.dp),
                    )
                    AppKitLinkButton(
                        text = "TV Shows",
                    ) {
                        router(Router.Tv)
                    }

                    AppKitLinkButton(
                        text = "Movies",
                    ) {
                        router(Router.Movie)
                    }

                    AppKitLinkButton(
                        text = "My List",
                    ) {
                        router(Router.Favorite)
                    }
                }
            })
        },
        bottomBar = {
        },
        content = content,
    )
}
