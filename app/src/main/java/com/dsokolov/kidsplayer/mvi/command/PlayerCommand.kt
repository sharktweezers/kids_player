package com.dsokolov.kidsplayer.mvi.command

internal sealed interface PlayerCommand {

    data object PlayingItemPage : PlayerCommand

    data object PlayPauseClicked : PlayerCommand
}