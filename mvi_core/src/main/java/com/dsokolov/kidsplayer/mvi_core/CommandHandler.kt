package com.dsokolov.kidsplayer.mvi_core

import kotlinx.coroutines.flow.Flow

/**
 * Обобщенный обработчик команд [Command]
 * испускаемых [стейт-машиной].
 *
 * Получает испускаемый команды в [onCommand] и должен его обработать.
 * Результатом обработки команды может быть новое событие (или множество) отправляемое на вход в стейт-машину.
 * Так же обработка команд может завершиться и без каких-либо событий.
 *
 * Все команды могут обрабатываться ассинхронно,
 * а результаты обработки могут попадать в [eventSource] в любом порядке.
 *
 *  getEventSource наблюдаемый истоник результатов обработки команд
 */

interface CommandHandler<out Event, in Command> {

    fun getEventSource(): Flow<Event>

    suspend fun onCommand(command: Command)
}