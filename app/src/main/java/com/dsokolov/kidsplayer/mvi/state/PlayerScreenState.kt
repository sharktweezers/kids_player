package com.dsokolov.kidsplayer.mvi.state

import com.dsokolov.kidsplayer.domain.model.PlayerData

internal data class PlayerScreenState(
    val isVerticalScreenOrientation: Boolean,
    val playerData: PlayerData?,
)