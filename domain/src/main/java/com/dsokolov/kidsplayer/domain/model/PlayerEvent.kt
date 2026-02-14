package com.dsokolov.kidsplayer.domain.model

internal data class PlayerEvent(val eventType: PlayerEventType?)

sealed interface PlayerEventType {

    data object PlayBtnClicked : PlayerEventType

    data class PageChanged(val page: Int) : PlayerEventType

    data class PlayableItemChanged(val itemId: Int) : PlayerEventType
}