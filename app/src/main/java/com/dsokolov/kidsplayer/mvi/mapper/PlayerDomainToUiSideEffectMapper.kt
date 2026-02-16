package com.dsokolov.kidsplayer.mvi.mapper

import com.dsokolov.kidsplayer.mvi.side_effect.PlayerSideEffect
import com.dsokolov.kidsplayer.presentation.PlayerUiSideEffect

internal class PlayerDomainToUiSideEffectMapper {

    fun map(mviSideEffect: PlayerSideEffect): PlayerUiSideEffect {
        return when (mviSideEffect) {
            is PlayerSideEffect.StartPlayerService -> PlayerUiSideEffect.StartPlayerService
            is PlayerSideEffect.ToPage -> PlayerUiSideEffect.ToPage(mviSideEffect.pageNumber)
        }
    }
}