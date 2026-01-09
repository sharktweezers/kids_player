package com.dsokolov.kidsplayer.mvi_core

/**
 * Модель изменения состояния стейт-машины.
 *
 * @property event событие которое привело к изменению состояния (даже если состояние не изменилось).
 * @property state состояние на момент получения события [event]
 * @property updatedState новое состояние после обработки события [event]
 * @property sideEffects сайд-эффекты выброшенные стейт-машиной после обработки события [event]
 * @property commands команды выброшенные стейт-машиной после обработки события [event]
 */
data class Transition<out Event, out State, out SideEffect, out Command> internal constructor(
        val state: State,
        val event: Event,
        val updatedState: State,
        val sideEffects: List<SideEffect>,
        val commands: List<Command>
)