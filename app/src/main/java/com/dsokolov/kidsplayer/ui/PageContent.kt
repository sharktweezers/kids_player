package com.dsokolov.kidsplayer.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.dsokolov.kidsplayer.domain.PlayerPage
import com.dsokolov.kidsplayer.ui.theme.BORDER_GRID_1
import com.dsokolov.kidsplayer.ui.theme.BORDER_GRID_2

@Composable
fun PageContent(page: PlayerPage) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(BORDER_GRID_2.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        item {
            FlowRow(
                modifier = Modifier.padding(
                    horizontal = BORDER_GRID_2.dp,
                    vertical = BORDER_GRID_1.dp,
                ),
                maxItemsInEachRow = 3,
                verticalArrangement = Arrangement.Center,
            ) {
                page.items.forEach { item ->
                    Image(
                        painter = painterResource(id = item.iconId),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}