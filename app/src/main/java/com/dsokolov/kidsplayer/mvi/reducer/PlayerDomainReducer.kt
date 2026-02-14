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
        }
    }

    private fun reducePlayerData(
        event: DomainEvent.PlayerDataEvent,
        state: State,
    ): Update<State, SideEffect, Command> {
        updateState { state.copy(playerData = event.playerData) }
        if (state.playerData?.isPlay == true) {
            sideEffect { SideEffect.StartPlayerService }
        }

        return buildUpdate(state)
    }
}