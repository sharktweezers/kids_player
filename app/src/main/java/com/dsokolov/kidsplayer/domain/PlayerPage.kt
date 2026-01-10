package com.dsokolov.kidsplayer.domain

import com.dsokolov.kidsplayer.presentation.PlayableItem

class PlayerPage(
    val items: List<PlayableItem>,
    val columnsCount: Int,
)