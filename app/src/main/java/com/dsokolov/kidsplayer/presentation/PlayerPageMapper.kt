package com.dsokolov.kidsplayer.presentation

import com.dsokolov.kidsplayer.domain.model.PlayerPage

object PlayerPageMapper {
    private const val COLUMN_COUNT_IN_PORTRAIT = 3
    private const val COLUMN_COUNT_IN_LANDSCAPE = 6

    fun domainToUiPage(
        playerPages: List<PlayerPage>,
        isVerticalScreenOrientation: Boolean,
    ): List<UiPlayerPage> {
        return playerPages.map { page ->
            UiPlayerPage(
                items = page.items,
                columnsCount = if (isVerticalScreenOrientation) {
                    COLUMN_COUNT_IN_PORTRAIT
                } else {
                    COLUMN_COUNT_IN_LANDSCAPE
                }
            )
        }
    }
}