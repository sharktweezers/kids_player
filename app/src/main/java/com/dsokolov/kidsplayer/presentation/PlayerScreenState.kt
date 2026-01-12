package com.dsokolov.kidsplayer.presentation

import androidx.compose.runtime.Immutable
import com.dsokolov.kidsplayer.domain.PlayerPage

@Immutable
data class PlayerScreenState(
    val pagesCount: Int,
    val pageContent: PlayerPage,
    val currentItemId: Int?,
    val isVerticalScreenOrientation: Boolean,
)