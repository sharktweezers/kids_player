package com.dsokolov.kidsplayer.domain.model

sealed interface PlayerSideEffect {

    sealed interface PlayerServiceSideEffect : PlayerSideEffect {

        data object Stop : PlayerServiceSideEffect

        data object Play : PlayerServiceSideEffect

        data class PlayMediaId(val playableItem: PlayableItem) : PlayerServiceSideEffect
    }

    data class ToPage(val pageNumber: Int) : PlayerSideEffect
}