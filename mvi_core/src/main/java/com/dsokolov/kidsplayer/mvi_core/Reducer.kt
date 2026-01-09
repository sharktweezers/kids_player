package com.dsokolov.kidsplayer.mvi_core

/**
 * Преобразователь текущего состояния [State] в [Update] при получении входного события [Event]
 * [Event] - событие, вызванное из ui или из domain
 * [State] - единственное, полное, всегда актуальное доменное состояние
 * [Command] - команда, идущая в сторону доменного слоя (загрузка данных, сохранение, и т.д)
 * [SideEffect] - эффект, идущий в сторону ui слоя (навигация, алерты, диалоги и т.д.)
 */
interface Reducer<in Event, State, out SideEffect, out Command> {

    fun update(state: State, event: Event): Update<State, SideEffect, Command>

}

abstract class ReducerDsl<in Event, State, SideEffect, Command> : Reducer<Event, State, SideEffect, Command> {

    private var commands: MutableList<Command> = mutableListOf()
    private var sideEffects: MutableList<SideEffect> = mutableListOf()
    private var states: MutableList<State.() -> State> = mutableListOf()

    override fun update(state: State, event: Event): Update<State, SideEffect, Command> {
        return buildUpdate(state)
    }

    protected fun <Event, State, SideEffect, Command> ReducerDsl<Event, State, SideEffect, Command>.commands(vararg command: Command) {
        commands.addAll(command.toList())
    }

    protected fun <Event, State, SideEffect, Command> ReducerDsl<Event, State, SideEffect, Command>.command(
            command: () -> Command?
    ) {
        command()?.let(commands::add)
    }

    protected fun <Event, State, SideEffect, Command> ReducerDsl<Event, State, SideEffect, Command>.sideEffect(
            sideEffect: () -> SideEffect?
    ) {
        sideEffect()?.let(sideEffects::add)
    }

    protected fun <Event, State, SideEffect, Command> ReducerDsl<Event, State, SideEffect, Command>.updateState(state: State.() -> State) {
        states.add(state)
    }

    protected fun <Event, State, SideEffect, Command> ReducerDsl<Event, State, SideEffect, Command>.nothing() = Update.nothing<State, SideEffect, Command>()

    protected fun <Event, State, SideEffect, Command> ReducerDsl<Event, State, SideEffect, Command>.buildUpdate(
            initialState: State
    ): Update<State, SideEffect, Command> {
        val state = if (states.isNotEmpty()) {
            states.fold(initialState) { state, updateState -> state.updateState() }
        } else {
            null
        }
        val newUpdate = Update<State, SideEffect, Command>(state = state, sideEffects = sideEffects, commands = commands)
        sideEffects = mutableListOf()
        commands = mutableListOf()
        states = mutableListOf()
        return newUpdate
    }

}