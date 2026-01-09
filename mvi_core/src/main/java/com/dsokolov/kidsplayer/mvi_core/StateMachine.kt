package com.dsokolov.kidsplayer.mvi_core

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow

// Для более простой реализации MVI можно использовать https://github.com/orbit-mvi/orbit-mvi/tree/main

internal class StateMachine<Event, State, SideEffect, Command> (
    private val stateUpdater: Reducer<Event, State, SideEffect, Command>,
    initialState: State
) {
    private val mutableStateFlow = MutableStateFlow(initialState)

    val stateFlow = mutableStateFlow.asStateFlow()

    suspend fun onEvent(event: Event) {
        val update = stateUpdater.update(
            state = mutableStateFlow.value,
            event = event,
        )

        update.state?.let { updatedState ->
            mutableStateFlow.emit(updatedState)
        }

        update.commands
        update.sideEffects
    }

    /*fun getTransitionSource(): SharedFlow<Transition<Event, State, SideEffect, Command>> {

    }*/

}