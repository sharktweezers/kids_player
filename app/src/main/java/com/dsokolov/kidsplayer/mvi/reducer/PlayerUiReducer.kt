package com.dsokolov.kidsplayer.mvi.reducer

import com.dsokolov.kidsplayer.mvi_core.ReducerDsl
import com.dsokolov.kidsplayer.mvi_core.Update
import com.dsokolov.kidsplayer.mvi.command.PlayerCommand as Command
import com.dsokolov.kidsplayer.mvi.state.PlayerScreenState as State
import com.dsokolov.kidsplayer.mvi.event.PlayerEvent.UiPlayerEvent as UiEvent
import com.dsokolov.kidsplayer.mvi.side_effect.PlayerSideEffect as SideEffect

class PlayerUiReducer : ReducerDsl<UiEvent, State, SideEffect, Command>() {

    override fun update(
        state: State,
        event: UiEvent
    ): Update<State, SideEffect, Command> {
        return when (event) {
            is UiEvent.PageChanged -> reducePageChanged(event, state)
            is UiEvent.ScreenOrientationChanged -> reduceScreenOrientation(event, state)
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
            updateState { state.copy(playerData = data.copy(currentPageNumber = event.pageNumber)) }
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
}