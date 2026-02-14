package com.dsokolov.kidsplayer.mvi.handler

import com.dsokolov.kidsplayer.domain.interactor.PlayerInteractor
import com.dsokolov.kidsplayer.domain.model.PlayerEventType
import com.dsokolov.kidsplayer.mvi_core.CommandHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import com.dsokolov.kidsplayer.mvi.event.PlayerEvent.DomainPlayerEvent as Event
import com.dsokolov.kidsplayer.mvi.command.PlayerCommand as Command

@OptIn(ExperimentalCoroutinesApi::class)
internal class PlayerCommandHandler(
    private val playerInteractor: PlayerInteractor
) : CommandHandler<Event, Command> {

    private val commandSharedFlow = MutableSharedFlow<Command>()

    private fun playPauseClicked(): Flow<Event> {
        return flow {
            playerInteractor.onPlayerEventChanged(eventType = PlayerEventType.PlayBtnClicked)
        }
    }

    override fun getEventSource(): Flow<Event> {
        val commandsFlow = commandSharedFlow.flatMapMerge { command ->
            when (command) {
                Command.PlayPauseClicked -> playPauseClicked()
            }
        }

        val playerDataFlow = playerInteractor
            .getPlayerDataFlow()
            .map(Event::PlayerDataEvent)
            .flowOn(Dispatchers.IO)

        return listOf(
            playerDataFlow,
            commandsFlow,
        )
            .merge()
    }

    override suspend fun onCommand(command: Command) {
        commandSharedFlow.emit(command)
    }
}