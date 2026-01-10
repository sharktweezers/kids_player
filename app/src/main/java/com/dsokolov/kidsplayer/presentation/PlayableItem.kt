package com.dsokolov.kidsplayer.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes

data class PlayableItem(
    val id: Int,
    val markAsPlayed: Boolean,
    @get:DrawableRes val iconId: Int,
    @get:RawRes val audioId: Int,
)