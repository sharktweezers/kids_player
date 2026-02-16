package com.dsokolov.kidsplayer.domain.model

sealed interface PlayerEvent {

    data object PlayPauseBtnClicked : PlayerEvent

    data class PageChanged(val pageNumber: Int) : PlayerEvent

    data object StopService : PlayerEvent
}