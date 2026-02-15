package com.dsokolov.kidsplayer.presentation

internal sealed interface PlayerUiSideEffect {
    data object StartPlayerService : PlayerUiSideEffect

    data object StopPlayerService : PlayerUiSideEffect
}