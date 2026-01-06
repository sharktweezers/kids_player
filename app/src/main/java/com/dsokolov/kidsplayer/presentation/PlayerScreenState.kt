package com.dsokolov.kidsplayer.presentation

data class PlayerScreenState(
    val playableItems: List<PlayableItem>,
    val currentItemId: Int?,
    val isVerticalScreenOrientation: Boolean?,
)