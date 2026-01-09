package com.dsokolov.kidsplayer.mvi_core

/**
 * При необходимости слушать изменения состояния при реакции на события [Event]
 * можно предоставить [TransitionListener] в который будет брошена полная информация об [Transition],
 * содержащая данные о том какое событие случилось, какое состояние было, и новое состояние с набором сайд-эффектов и команд.
 * Если при получении события состояние не изменилось и не было "выброшено" сайд-эффектов или команд
 * [TransitionListener] все равно будет уведомлен о получении события.
 */
interface TransitionListener<in Event, in State, in SideEffect, in Command> {
    fun onTransition(transition: Transition<Event, State, SideEffect, Command>)
}