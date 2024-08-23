package com.instaleap.instaflix

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.instaleap.appkit.component.ItemCard
import com.instaleap.appkit.component.TopBarMovie
import com.instaleap.core.route.Router
import com.instaleap.instaflix.ui.theme.InstaflixTheme
import com.instaleap.presentation.nav.NavigatorApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstaflixTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    NavigatorApp()
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ItemCardPreview() {
    InstaflixTheme {
        TopBarMovie(
            content = { innerPadding ->
                LazyRow(
                    modifier =
                        Modifier
                            .padding(innerPadding)
                            .fillMaxSize(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    content = {
                        items(5) {
                            ItemCard("") {
                            }
                        }
                    },
                )
            },
            selected = Router.Tv,
            router = {
            },
        )
    }
}
