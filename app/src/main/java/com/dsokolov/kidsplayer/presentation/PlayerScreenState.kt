package com.dsokolov.kidsplayer.presentation

import com.dsokolov.kidsplayer.domain.PlayerPage

data class PlayerScreenState(
    val page: PlayerPage,
    val currentItemId: Int?,
    val isVerticalScreenOrientation: Boolean?,
)