package com.dsokolov.kidsplayer.mvi.reducer

import com.dsokolov.kidsplayer.mvi_core.ReducerDsl
import com.dsokolov.kidsplayer.mvi_core.Update
import com.dsokolov.kidsplayer.mvi.command.PlayerCommand as Command
import com.dsokolov.kidsplayer.mvi.state.PlayerScreenState as State
import com.dsokolov.kidsplayer.mvi.event.PlayerEvent.DomainPlayerEvent as DomainEvent
import com.dsokolov.kidsplayer.mvi.side_effect.PlayerSideEffect as SideEffect

internal class PlayerDomainReducer : ReducerDsl<DomainEvent, State, SideEffect, Command>() {

    override fun update(
        state: State,
        event: DomainEvent,
    ): Update<State, SideEffect, Command> {
        return when (event) {
            is DomainEvent.PlayerDataEvent -> reducePlayerData(event, state)
            is DomainEvent.ToPage -> reduceToPage(event, state)
        }
    }

    private fun reducePlayerData(
        event: DomainEvent.PlayerDataEvent,
        state: State,
    ): Update<State, SideEffect, Command> {
        updateState { state.copy(playerData = event.playerData) }
        if (event.playerData.isPlay) {
            sideEffect { SideEffect.StartPlayerService }
        }

        return buildUpdate(state)
    }

    private fun reduceToPage(
        event: DomainEvent.ToPage,
        state: State,
    ): Update<State, SideEffect, Command> {
        sideEffect { SideEffect.ToPage(event.pageNumber) }

        return buildUpdate(state)
    }
}