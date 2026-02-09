package com.dsokolov.kidsplayer.presentation

import androidx.compose.runtime.Immutable
import com.dsokolov.kidsplayer.domain.model.PlayableItem

@Immutable
data class UiPlayerPage(
    val items: List<PlayableItem>,
    val columnsCount: Int,
)