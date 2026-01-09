package com.dsokolov.kidsplayer.mvi_core

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * Полностью готовая обобщенная логика над [стейт-машиной]
 * уже связанной с [логикой обработки ее команд].
 *
 * На "выход" отдает актуальное состояние [State] и [SideEffect] реагирует на входящие события [Event].
 */
interface Store<in Event, out State, out SideEffect> {

    suspend fun onEvent(event: Event)

    fun start(
            coroutineScope: CoroutineScope,
            coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default,
            actionState: suspend (State) -> Unit,
            actionSideEffect: suspend (SideEffect) -> Unit,
            coroutineExceptionHandler: CoroutineExceptionHandler? = null,
    )
}