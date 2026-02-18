package com.dsokolov.kidsplayer.mvi.event

import com.dsokolov.kidsplayer.domain.model.PlayerData

internal sealed interface PlayerEvent {

    sealed interface UiPlayerEvent : PlayerEvent {

        class ScreenOrientationChanged(val isVerticalScreenOrientation: Boolean) : UiPlayerEvent

        class PageChanged(val pageNumber: Int) : UiPlayerEvent

        data object RepeatClicked : UiPlayerEvent

        data object PlayPauseClicked : UiPlayerEvent

        data object NextClicked : UiPlayerEvent

        data class ItemClicked(val itemId: Int) : UiPlayerEvent
    }

    sealed interface DomainPlayerEvent : PlayerEvent {

        data class PlayerDataEvent(val playerData: PlayerData) : DomainPlayerEvent

        data class ToPage(val pageNumber: Int) : DomainPlayerEvent
    }
}