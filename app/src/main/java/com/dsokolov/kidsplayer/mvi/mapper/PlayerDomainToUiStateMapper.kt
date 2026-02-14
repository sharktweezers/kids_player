package com.dsokolov.kidsplayer.mvi.mapper

import com.dsokolov.kidsplayer.mvi.state.PlayerScreenState
import com.dsokolov.kidsplayer.presentation.UiPlayerState

internal class PlayerDomainToUiStateMapper {
    fun map(playerScreenState: PlayerScreenState): UiPlayerState {
        val data = playerScreenState.playerData

        return if (data == null) {
            UiPlayerState.UiPlayerLoading
        } else {
            UiPlayerState.UiPlayerFill(
                isPlay = data.isPlay,
                pagesCount = data.pagesCount,
                currentPage = data.currentPageNumber,
                pages = data.pages,
                columnsCount = if (playerScreenState.isVerticalScreenOrientation) {
                    COLUMN_COUNT_IN_PORTRAIT
                } else {
                    COLUMN_COUNT_IN_LANDSCAPE
                }
            )
        }
    }

    private companion object {
        private const val COLUMN_COUNT_IN_PORTRAIT = 3
        private const val COLUMN_COUNT_IN_LANDSCAPE = 6
    }
}