package com.dsokolov.kidsplayer.mvi.event

import com.dsokolov.kidsplayer.domain.model.PlayerData

sealed interface PlayerEvent {

    sealed interface UiPlayerEvent : PlayerEvent

    sealed interface DomainPlayerEvent : PlayerEvent {
        data class PlayerDataEvent(val playerData: PlayerData) : DomainPlayerEvent
    }
}