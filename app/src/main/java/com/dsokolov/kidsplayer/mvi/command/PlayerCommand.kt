package com.dsokolov.kidsplayer.mvi.command

internal sealed interface PlayerCommand {

    data object InitUi : PlayerCommand

    data class PageChanged(val pageNumber: Int) : PlayerCommand

    data object PlayPauseClicked : PlayerCommand

    data object RepeatClicked : PlayerCommand

    data object NextClicked : PlayerCommand

    data class ItemClicked(val itemId: Int) : PlayerCommand
}