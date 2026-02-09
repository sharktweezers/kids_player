package com.dsokolov.kidsplayer.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class PlayableItem(
    val id: Int,
    val markAsPlayed: Boolean,
    @get:DrawableRes val iconId: Int,
    @get:RawRes val audioId: Int,
)