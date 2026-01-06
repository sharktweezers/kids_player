package com.dsokolov.kidsplayer.mvi

import com.dsokolov.kidsplayer.presentation.PlayerScreenState

class PlayerScreenStore() {
    fun getInitialPlayerScreenState(isVerticalScreenOrientation: Boolean): PlayerScreenState {
        return PlayerScreenState(
            playableItems = emptyList(),
            currentItemId = null,
            isVerticalScreenOrientation = isVerticalScreenOrientation,
        )
    }
}