package com.dsokolov.kidsplayer.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class PlayerScreenState(
    val pagesCount: Int,
    val currentPage: Int,
    val currentItemId: Int?,
    val isVerticalScreenOrientation: Boolean,
    private val playerPages: List<UiPlayerPage>,
) {
    val pages: List<UiPlayerPage>
        get() = playerPages.toList()
}