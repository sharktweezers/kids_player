package com.dsokolov.kidsplayer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dsokolov.kidsplayer.presentation.PlayableItem
import com.dsokolov.kidsplayer.ui.theme.BORDER_GRID_1
import com.dsokolov.kidsplayer.ui.theme.BORDER_GRID_2

@Composable
fun PageContent() {
    val pageItems = listOf<PlayableItem>()
    val itemsState by remember { mutableStateOf(pageItems) }
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

            }
        }
    }
}

@Preview
@Composable
private fun PreviewVerticallyPageContent() {
    PageContent()
}