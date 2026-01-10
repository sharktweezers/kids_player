package com.dsokolov.kidsplayer.domain

import com.dsokolov.kidsplayer.presentation.PlayableItem
import kotlin.math.max
import kotlin.math.min

object PlayerPaginator {
    fun getPage(): PlayerPage {
        val items = mutableListOf<PlayableItem>()

        val startIndex = max(0, 0)
        val endIndex = min(9, PlayableItemsStore.items.size)

        for (i in startIndex until endIndex) {
            items.add(PlayableItemsStore.items[i])
        }

        return PlayerPage(items)
    }
}