package com.dsokolov.kidsplayer.presentation

import androidx.annotation.RawRes
import androidx.annotation.StringRes

data class PlayableItem(
    val id: Int,
    val markAsPlayed: Boolean,
    @field:StringRes val iconId: Int,
    @field:RawRes val audioId: Int,
)