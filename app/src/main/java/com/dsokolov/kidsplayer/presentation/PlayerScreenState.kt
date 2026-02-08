package com.dsokolov.kidsplayer.presentation

import androidx.compose.runtime.Immutable
import com.dsokolov.kidsplayer.domain.PlayerPage

@Immutable
data class PlayerScreenState(
    val pagesCount: Int,
    val currentPage: Int,
    val currentItemId: Int?,
    val isVerticalScreenOrientation: Boolean,
    private val playerPages: List<PlayerPage>,
) {
    val pages: List<PlayerPage>
        get() = playerPages.toList()
}