package com.dsokolov.kidsplayer.domain.model

sealed interface PlayerEvent {

    data object PlayingItemPage : PlayerEvent

    data object PlayBtnClicked : PlayerEvent

    data object Stop : PlayerEvent

    data class PageChanged(val page: Int) : PlayerEvent

    data class PlayableItemChanged(val itemId: Int) : PlayerEvent
}