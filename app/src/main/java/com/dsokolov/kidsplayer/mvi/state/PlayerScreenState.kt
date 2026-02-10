package com.dsokolov.kidsplayer.mvi.state

import com.dsokolov.kidsplayer.domain.model.PlayerData

data class PlayerScreenState(
    val isVerticalScreenOrientation: Boolean,
    val playerData: PlayerData?,
)