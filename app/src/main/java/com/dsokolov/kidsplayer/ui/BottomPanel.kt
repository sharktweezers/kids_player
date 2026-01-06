package com.dsokolov.kidsplayer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dsokolov.kidsplayer.R
import com.dsokolov.kidsplayer.ui.theme.BORDER_GRID_1
import com.dsokolov.kidsplayer.ui.theme.CONTROLS_ITEM_SIDE
import com.dsokolov.kidsplayer.ui.theme.CONTROLS_PADDING

@Composable
fun BottomPanel() {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
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
                modifier = Modifier.size(
                    width = CONTROLS_ITEM_SIDE.dp,
                    height = CONTROLS_ITEM_SIDE.dp
                ),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Preview
@Composable
private fun BottomPanelPreview() {
    BottomPanel()
}