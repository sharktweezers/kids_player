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
        return Update.nothing()
    }
}