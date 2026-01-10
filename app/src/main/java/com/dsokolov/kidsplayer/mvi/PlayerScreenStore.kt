package com.dsokolov.kidsplayer.mvi

import com.dsokolov.kidsplayer.domain.PlayerPaginator
import com.dsokolov.kidsplayer.presentation.PlayerScreenState

class PlayerScreenStore() {
    fun getInitialPlayerScreenState(isVerticalScreenOrientation: Boolean): PlayerScreenState {
        return PlayerScreenState(
            page = PlayerPaginator.getPage(),
            currentItemId = null,
            isVerticalScreenOrientation = isVerticalScreenOrientation,
        )
    }
}