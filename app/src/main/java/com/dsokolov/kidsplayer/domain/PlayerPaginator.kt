package com.dsokolov.kidsplayer.domain

import com.dsokolov.kidsplayer.presentation.PlayableItem
import kotlin.math.max
import kotlin.math.min

object PlayerPaginator {
    fun getPage(isVerticalScreenOrientation: Boolean): PlayerPage {
        val items = mutableListOf<PlayableItem>()

        val startIndex = max(0, 0)
        val endIndex = min(12, PlayableItemsStore.items.size)

        for (i in 0 until endIndex) {
            items.add(PlayableItemsStore.items[i])
        }

        return PlayerPage(
            items = items,
            columnsCount = if (isVerticalScreenOrientation) 3 else 6
        )
    }
}