package com.dsokolov.kidsplayer.mvi.factory

import com.dsokolov.kidsplayer.mvi.handler.PlayerCommandHandler
import com.dsokolov.kidsplayer.mvi.reducer.PlayerReducer
import com.dsokolov.kidsplayer.mvi_core.Store
import com.dsokolov.kidsplayer.mvi_core.StoreFactory
import com.dsokolov.kidsplayer.utils.DispatchersProvider
import com.dsokolov.kidsplayer.mvi.state.PlayerScreenState as State
import com.dsokolov.kidsplayer.mvi.event.PlayerEvent as Event
import com.dsokolov.kidsplayer.mvi.side_effect.PlayerSideEffect as SideEffect

internal class PlayerStoreFactory(
    private val reducer: PlayerReducer,
    private val commandHandler: PlayerCommandHandler,
    private val dispatchersProvider: DispatchersProvider,
) {
    fun createStore(
        isVerticalScreenOrientation: Boolean,
    ): Store<Event, State, SideEffect> {
        return StoreFactory.createStore(
            coroutineDispatcher = dispatchersProvider.default,
            stateUpdater = reducer,
            initialState = reducer.getInitialPlayerState(isVerticalScreenOrientation),
            initialCommands = reducer.getInitialCommands(),
            commandHandler = commandHandler,
        )
    }
}