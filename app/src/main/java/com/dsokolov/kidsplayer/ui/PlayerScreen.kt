package com.dsokolov.kidsplayer.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dsokolov.kidsplayer.R
import com.dsokolov.kidsplayer.ui.theme.BORDER_GRID_1
import com.dsokolov.kidsplayer.ui.theme.BORDER_GRID_2
import com.dsokolov.kidsplayer.ui.theme.CONTROLS_ITEM_SIDE
import com.dsokolov.kidsplayer.ui.theme.CONTROLS_PADDING
import com.dsokolov.kidsplayer.ui.theme.KidsPlayerTheme

@Composable
fun PlayerScene() {
    KidsPlayerTheme {
        Scaffold(
            modifier = Modifier.Companion
                .fillMaxSize()
                .padding(BORDER_GRID_2.dp)
        ) { innerPadding ->
            Image(
                painter = painterResource(id = R.drawable.gradient),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            BottomPanel(innerPadding)
        }
    }
}