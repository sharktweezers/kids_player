package com.dsokolov.kidsplayer.presentation

import androidx.compose.runtime.Immutable
import com.dsokolov.kidsplayer.domain.model.PlayerPage

sealed interface UiPlayerState {

    data object UiPlayerLoading : UiPlayerState

    @Immutable
    data class UiPlayerFill(
        val pagesCount: Int,
        val currentPage: Int,
        val pages: List<PlayerPage>,
        val columnsCount: Int,
    ) : UiPlayerState
}
