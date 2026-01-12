package com.dsokolov.kidsplayer.domain

import androidx.compose.runtime.Immutable
import com.dsokolov.kidsplayer.presentation.PlayableItem

@Immutable
data class PlayerPage(
    val items: List<PlayableItem>,
    val columnsCount: Int,
    val pageNumber: Int,
)