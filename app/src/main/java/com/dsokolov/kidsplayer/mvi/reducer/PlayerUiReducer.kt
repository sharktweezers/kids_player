package com.dsokolov.kidsplayer.mvi.reducer

import com.dsokolov.kidsplayer.mvi_core.ReducerDsl
import com.dsokolov.kidsplayer.mvi_core.Update
import com.dsokolov.kidsplayer.mvi.command.PlayerCommand as Command
import com.dsokolov.kidsplayer.mvi.state.PlayerScreenState as State
import com.dsokolov.kidsplayer.mvi.event.PlayerEvent.UiPlayerEvent as UiEvent
import com.dsokolov.kidsplayer.mvi.side_effect.PlayerSideEffect as SideEffect

internal class PlayerUiReducer : ReducerDsl<UiEvent, State, SideEffect, Command>() {

    override fun update(
        state: State,
        event: UiEvent,
    ): Update<State, SideEffect, Command> {
        return when (event) {
            is UiEvent.PageChanged -> reducePageChanged(event, state)
            is UiEvent.ScreenOrientationChanged -> reduceScreenOrientation(event, state)
            is UiEvent.PlayPauseClicked -> reducePlayPauseClicked(state)
            is UiEvent.RepeatClicked -> reduceRepeatClicked(state)
            is UiEvent.ItemClicked -> reduceItemClicked(event, state)
            is UiEvent.NextClicked -> nextClicked(state)
        }
    }

    private fun reducePageChanged(
        event: UiEvent.PageChanged,
        state: State,
    ): Update<State, SideEffect, Command> {
        val data = state.playerData
        if (data == null) {
            return Update.nothing()
        } else {
            command { Command.PageChanged(event.pageNumber) }
        }

        return buildUpdate(state)
    }

    private fun reduceScreenOrientation(
        event: UiEvent.ScreenOrientationChanged,
        state: State,
    ): Update<State, SideEffect, Command> {
        updateState { state.copy(isVerticalScreenOrientation = event.isVerticalScreenOrientation) }

        return buildUpdate(state)
    }

    private fun reducePlayPauseClicked(
        state: State,
    ): Update<State, SideEffect, Command> {
        command { Command.PlayPauseClicked }

        return buildUpdate(state)
    }

    private fun reduceRepeatClicked(
        state: State,
    ): Update<State, SideEffect, Command> {
        command { Command.RepeatClicked }

        return buildUpdate(state)
    }

    private fun reduceItemClicked(
        event: UiEvent.ItemClicked,
        state: State,
    ): Update<State, SideEffect, Command> {
        command { Command.ItemClicked(event.itemId) }

        return buildUpdate(state)
    }

    private fun nextClicked(state: State): Update<State, SideEffect, Command> {
        command { Command.NextClicked }

        return buildUpdate(state)
    }
}