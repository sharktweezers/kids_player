package com.dsokolov.kidsplayer.mvi

import com.dsokolov.kidsplayer.domain.PlayerPaginator
import com.dsokolov.kidsplayer.presentation.PlayerScreenState

class PlayerScreenStore() {
    fun getInitialPlayerScreenState(isVerticalScreenOrientation: Boolean): PlayerScreenState {
        return PlayerScreenState(
            pagesCount = PlayerPaginator.getPagesCount(),
            pageContent = PlayerPaginator.getPageContent(
                pageNumber = 0,
                isVerticalScreenOrientation = isVerticalScreenOrientation
            ),
            currentItemId = null,
            isVerticalScreenOrientation = isVerticalScreenOrientation,
        )
    }
}