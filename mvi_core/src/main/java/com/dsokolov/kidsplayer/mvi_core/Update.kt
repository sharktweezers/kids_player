package com.dsokolov.kidsplayer.mvi_core

/**
 * На основе объекта этого класса происходит обновление состояния [State] и/или генерация [SideEffect] и [Command]
 * [State] - единственное, полное, всегда актуальное доменное состояние
 * [Command] - команда, идущая в сторону доменного слоя (загрузка данных, сохранение, и т.д)
 * [SideEffect] - эффект, идущий в сторону ui слоя (навигация, алерты, диалоги и т.д.)
 */
data class Update<out State, out SideEffect, out Command> internal constructor(
        val state: State?,
        val sideEffects: List<SideEffect>?,
        val commands: List<Command>?,
) {

    companion object {
        fun <State, SideEffect, Command> nothing(): Update<State, SideEffect, Command> = Update(null, null, null)

        fun <State, SideEffect, Command> state(state: State) = Update<State, SideEffect, Command>(state = state, sideEffects = null, commands = null)

        fun <State, SideEffect, Command> sideEffects(vararg sideEffects: SideEffect) = Update<State, SideEffect, Command>(state = null, sideEffects = sideEffects.ifEmpty { null }?.toList(), commands = null)

        fun <State, SideEffect, Command> sideEffects(sideEffects: List<SideEffect>) = Update<State, SideEffect, Command>(state = null, sideEffects = sideEffects, commands = null)

        fun <State, SideEffect, Command> commands(vararg commands: Command) = Update<State, SideEffect, Command>(state = null, sideEffects = null, commands = commands.ifEmpty { null }?.toList())

        fun <State, SideEffect, Command> commands(commands: List<Command>) = Update<State, SideEffect, Command>(state = null, sideEffects = null, commands = commands)

    }
}

fun <State, SideEffect, Command> Update<State, SideEffect, Command>.state(newState: State): Update<State, SideEffect, Command> {
    return copy(state = newState)
}

fun <State, SideEffect, Command> Update<State, SideEffect, Command>.sideEffects(vararg newSideEffects: SideEffect): Update<State, SideEffect, Command> {
    if (newSideEffects.isEmpty()) return this
    return copy(sideEffects = newSideEffects.toList())
}

fun <State, SideEffect, Command> Update<State, SideEffect, Command>.sideEffects(newSideEffects: List<SideEffect>): Update<State, SideEffect, Command> {
    if (newSideEffects.isEmpty()) return this
    return copy(sideEffects = newSideEffects)
}

fun <State, SideEffect, Command> Update<State, SideEffect, Command>.commands(vararg newCommands: Command): Update<State, SideEffect, Command> {
    if (newCommands.isEmpty()) return this
    return copy(commands = newCommands.toList())
}

fun <State, SideEffect, Command> Update<State, SideEffect, Command>.commands(newCommands: List<Command>): Update<State, SideEffect, Command> {
    if (newCommands.isEmpty()) return this
    return copy(commands = newCommands)
}

