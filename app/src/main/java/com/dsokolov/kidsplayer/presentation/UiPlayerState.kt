package com.dsokolov.kidsplayer.presentation

import androidx.compose.runtime.Stable
import com.dsokolov.kidsplayer.domain.model.PlayerPage

internal sealed interface UiPlayerState {

    data object UiPlayerLoading : UiPlayerState

    @Stable
    data class UiPlayerFill(
        val currentItemId: Int?,
        val isPlay: Boolean,
        val pagesCount: Int,
        val currentPage: Int,
        val pages: List<PlayerPage>,
        val columnsCount: Int,
    ) : UiPlayerState
}
