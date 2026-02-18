package com.dsokolov.kidsplayer.domain.model

sealed interface PlayerEvent {

    data object RepeatClicked : PlayerEvent

    data object PlayPauseBtnClicked : PlayerEvent

    data object NextClicked : PlayerEvent

    data class ItemClicked(val itemId: Int) : PlayerEvent

    data class PageChanged(val pageNumber: Int) : PlayerEvent

    data object InitUi : PlayerEvent

    data object OnCreateService : PlayerEvent

    data object OnDestroyService : PlayerEvent
}