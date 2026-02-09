package com.dsokolov.kidsplayer.mvi

import com.dsokolov.kidsplayer.domain.usecase.GetPlayerPagesCountUseCases
import com.dsokolov.kidsplayer.domain.usecase.GetPlayerPagesUseCases
import com.dsokolov.kidsplayer.presentation.PlayerPageMapper
import com.dsokolov.kidsplayer.presentation.PlayerScreenState

class PlayerScreenStore(
    private val getPlayerPagesCountUseCases: GetPlayerPagesCountUseCases,
    private val getPlayerPagesUseCases: GetPlayerPagesUseCases,
) {
    fun getInitialPlayerScreenState(isVerticalScreenOrientation: Boolean): PlayerScreenState {
        return PlayerScreenState(
            pagesCount = getPlayerPagesCountUseCases(),
            currentPage = 0,
            currentItemId = null,
            isVerticalScreenOrientation = isVerticalScreenOrientation,
            playerPages = PlayerPageMapper.domainToUiPage(getPlayerPagesUseCases(), isVerticalScreenOrientation),
        )
    }
}