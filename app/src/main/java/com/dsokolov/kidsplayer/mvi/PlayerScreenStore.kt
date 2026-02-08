package com.dsokolov.kidsplayer.mvi

import com.dsokolov.kidsplayer.domain.PlayerPaginator
import com.dsokolov.kidsplayer.presentation.PlayerScreenState

class PlayerScreenStore() {
    fun getInitialPlayerScreenState(isVerticalScreenOrientation: Boolean): PlayerScreenState {
        return PlayerScreenState(
            pagesCount = PlayerPaginator.getPagesCount(),
            currentPage = 0,
            currentItemId = null,
            isVerticalScreenOrientation = isVerticalScreenOrientation,
            playerPages = PlayerPaginator.getPages(
                isVerticalScreenOrientation = isVerticalScreenOrientation,
            ),
        )
    }
}