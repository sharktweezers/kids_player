package com.dsokolov.kidsplayer.mvi.command

internal sealed interface PlayerCommand {

    data class PageChanged(val pageNumber: Int) : PlayerCommand

    data object PlayPauseClicked : PlayerCommand
}