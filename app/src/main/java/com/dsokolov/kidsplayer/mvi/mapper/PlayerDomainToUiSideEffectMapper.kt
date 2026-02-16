package com.dsokolov.kidsplayer.mvi.mapper

import com.dsokolov.kidsplayer.mvi.side_effect.PlayerSideEffect
import com.dsokolov.kidsplayer.presentation.PlayerUiSideEffect

internal class PlayerDomainToUiSideEffectMapper {
    fun map(mviSideEffect: PlayerSideEffect): PlayerUiSideEffect {
        return when (val sideEffect = mviSideEffect) {
            PlayerSideEffect.StartPlayerService -> PlayerUiSideEffect.StartPlayerService
        }
    }
}