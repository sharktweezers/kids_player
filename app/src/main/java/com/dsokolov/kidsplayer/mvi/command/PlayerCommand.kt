package com.dsokolov.kidsplayer.mvi.command

sealed interface PlayerCommand {
    data object GetPlayerData : PlayerCommand
}