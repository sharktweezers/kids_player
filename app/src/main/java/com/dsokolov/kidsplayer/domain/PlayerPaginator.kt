package com.dsokolov.kidsplayer.domain

import com.dsokolov.kidsplayer.presentation.PlayableItem
import kotlin.math.min
import kotlin.math.ceil

object PlayerPaginator {
    private const val START_PAGE = 0
    private const val COLUMN_COUNT_IN_PORTRAIT = 3
    private const val COLUMN_COUNT_IN_LANDSCAPE = 6
    private const val PAGE_ITEMS = 12

    fun getPages(isVerticalScreenOrientation: Boolean): List<PlayerPage> {
        val pages = mutableListOf<PlayerPage>()

        for (page in START_PAGE until getPagesCount()) {
            pages.add(
                getPage(
                    pageNumber = page,
                    isVerticalScreenOrientation = isVerticalScreenOrientation,
                )
            )
        }

        return pages
    }

    fun getPage(
        pageNumber: Int,
        isVerticalScreenOrientation: Boolean,
    ): PlayerPage {
        val items = mutableListOf<PlayableItem>()
        val startIndex = pageNumber * PAGE_ITEMS
        val endIndex = min(startIndex + PAGE_ITEMS, PlayableItemsStore.items.size)

        for (i in startIndex until endIndex) {
            items.add(PlayableItemsStore.items[i])
        }

        return PlayerPage(
            items = items,
            columnsCount = if (isVerticalScreenOrientation) {
                COLUMN_COUNT_IN_PORTRAIT
            } else {
                COLUMN_COUNT_IN_LANDSCAPE
            },
            pageNumber = pageNumber,
        )
    }

    fun getPagesCount() = ceil(PlayableItemsStore.items.size.toFloat() / PAGE_ITEMS).toInt()
}