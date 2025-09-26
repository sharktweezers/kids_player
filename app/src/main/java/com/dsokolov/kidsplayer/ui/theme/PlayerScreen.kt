package com.dsokolov.kidsplayer.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.dsokolov.kidsplayer.R

private const val BORDER_GRID_1 = 8
private const val BORDER_GRID_2 = 16
private const val CONTROLS_PADDING = 75
private const val CONTROLS_ITEM_SIDE = 60

@Composable
fun PlayerScene() {
    KidsPlayerTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .padding(BORDER_GRID_2.dp)
        ) { innerPadding ->
            Image(
                painter = painterResource(id = R.drawable.gradient),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
            ) {
                Text(
                    text = "First item",
                    modifier = Modifier.padding(bottom = CONTROLS_PADDING.dp)
                )
                Row(
                    verticalAlignment = CenterVertically,
                    modifier = Modifier
                        .height(CONTROLS_PADDING.dp)
                        .align(Alignment.BottomCenter)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.repeat_all),
                        contentDescription = null,
                        modifier = Modifier
                            .size(width = CONTROLS_ITEM_SIDE.dp, height = CONTROLS_ITEM_SIDE.dp)
                            .padding(end = BORDER_GRID_1.dp),
                        contentScale = ContentScale.Crop
                    )
                    Image(
                        painter = painterResource(id = R.drawable.repeat),
                        contentDescription = null,
                        modifier = Modifier
                            .size(width = CONTROLS_ITEM_SIDE.dp, height = CONTROLS_ITEM_SIDE.dp)
                            .padding(end = BORDER_GRID_1.dp),
                        contentScale = ContentScale.Crop,
                    )
                    Image(
                        painter = painterResource(id = R.drawable.play),
                        contentDescription = null,
                        modifier = Modifier
                            .size(width = CONTROLS_ITEM_SIDE.dp, height = CONTROLS_ITEM_SIDE.dp)
                            .padding(end = BORDER_GRID_1.dp),
                        contentScale = ContentScale.Crop,
                    )
                    Image(
                        painter = painterResource(id = R.drawable.next),
                        contentDescription = null,
                        modifier = Modifier.size(width = CONTROLS_ITEM_SIDE.dp, height = CONTROLS_ITEM_SIDE.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
        }
    }
}

