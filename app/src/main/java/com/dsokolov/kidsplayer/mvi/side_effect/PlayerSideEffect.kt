package com.dsokolov.kidsplayer.mvi.side_effect

internal sealed interface PlayerSideEffect {

    data object StartPlayerService : PlayerSideEffect

    data class ToPage(val pageNumber: Int) : PlayerSideEffect
}