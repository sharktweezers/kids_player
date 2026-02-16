package com.dsokolov.kidsplayer.domain.model

sealed interface PlayerSideEffect {

    data object Stop : PlayerSideEffect

    data class ToPage(val pageNumber: Int) : PlayerSideEffect

    data class PlayMediaId(val playableItem: PlayableItem) : PlayerSideEffect
}