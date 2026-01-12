package com.dsokolov.kidsplayer.mvi_core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.scan

/**
 * Стейт-машина какой-либо логики построеной на состоянии [State].
 *
 * Реагирует на внешние события [Event] приходящие через [eventSource] в методе [getStateSource],
 * результатом реакции на событие будет новое состояние проброшенное дальше по flow
 * набор сайд-эффектов брошенных в [sideEffectsSharedFlow].
 * набор команд брошенных в [commandsSharedFlow].
 * Если состояние не изменилось или в результате реакции на событие не было создано никаких сайд-эффектов
 * то соостветвующие источники ничего не излучат.
 *
 * @property sideEffectsSharedFlow наблюдаемый источник излучаемых сайд-эффектов
 * @property commandsSharedFlow наблюдаемый источник излучаемых команд
 * @property transitionSharedFlow наблюдаемый источник излучаемых [Transition]
 */
class StateMachine<Event, out State, out SideEffect, Command>(
    private val stateUpdater: Reducer<Event, State, SideEffect, Command>,
    private val initialState: State,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default
) {

    private val eventSharedFlow = MutableSharedFlow<Event>(extraBufferCapacity = Int.MAX_VALUE)
    private val commandsSharedFlow = MutableSharedFlow<List<Command>>()
    private val sideEffectsSharedFlow = MutableSharedFlow<List<SideEffect>>()
    private val transitionSharedFlow = MutableSharedFlow<Transition<Event, State, SideEffect, Command>>(extraBufferCapacity = Int.MAX_VALUE)

    suspend fun onMessage(message: Event) {
        eventSharedFlow.emit(message)
    }

    fun getStateSource(onSubscription: suspend () -> Unit): Flow<State> {
        return eventSharedFlow
            .onSubscription { onSubscription.invoke() }
            .scan(initialState) { state, event ->
                try {
                    val (newState, effects, commands) = stateUpdater.update(state, event)
                    commands?.let { commandsSharedFlow.emit(it) }
                    effects?.let { sideEffectsSharedFlow.emit(it) }
                    sendTransition(state, event, newState, effects, commands)
                    newState ?: state
                } catch (e: Throwable) {
                    state
                } catch (e: Exception) {
                    state
                }
            }
            .distinctUntilChanged { oldState, newState -> oldState === newState }
            .flowOn(coroutineDispatcher)
    }

    fun getSideEffectSource(): SharedFlow<List<SideEffect>> {
        return sideEffectsSharedFlow.asSharedFlow()
    }

    fun getCommandSource(): SharedFlow<List<Command>> {
        return commandsSharedFlow.asSharedFlow()
    }

    fun getTransitionSource(): SharedFlow<Transition<Event, State, SideEffect, Command>> {
        return transitionSharedFlow.asSharedFlow()
    }

    suspend fun emitInitialCommands(initialCommands: List<Command>) {
        commandsSharedFlow.emit(initialCommands)
    }

    private suspend fun sendTransition(
        state: State,
        message: Event,
        newState: State?,
        sideEffects: List<SideEffect>?,
        commands: List<Command>?
    ) {
        val transition = Transition(
            state = state,
            event = message,
            updatedState = newState ?: state,
            sideEffects = sideEffects.orEmpty(),
            commands = commands.orEmpty()
        )
        transitionSharedFlow.emit(transition)
    }
}