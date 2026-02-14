package com.dsokolov.kidsplayer.mvi.reducer

import com.dsokolov.kidsplayer.mvi.state.PlayerScreenState
import com.dsokolov.kidsplayer.mvi_core.ReducerDsl
import com.dsokolov.kidsplayer.mvi_core.Update
import com.dsokolov.kidsplayer.mvi.command.PlayerCommand as Command
import com.dsokolov.kidsplayer.mvi.state.PlayerScreenState as State
import com.dsokolov.kidsplayer.mvi.event.PlayerEvent as Event
import com.dsokolov.kidsplayer.mvi.side_effect.PlayerSideEffect as SideEffect

internal class PlayerReducer(
    private val uiReducer: PlayerUiReducer,
    private val domainReducer: PlayerDomainReducer,
) : ReducerDsl<Event, State, SideEffect, Command>() {
    override fun update(
        state: State,
        event: Event
    ): Update<State, SideEffect, Command> {
        return when (event) {
            is Event.UiPlayerEvent -> uiReducer.update(state, event)
            is Event.DomainPlayerEvent -> domainReducer.update(state, event)
        }
    }

    fun getInitialPlayerState(isVerticalScreenOrientation: Boolean): State {
        return PlayerScreenState(
            isVerticalScreenOrientation = isVerticalScreenOrientation,
            playerData = null,
        )
    }

    fun getInitialCommands(): List<Command> {
        return emptyList()
    }
}