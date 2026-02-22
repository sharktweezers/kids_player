package com.dsokolov.kidsplayer.mvi.handler

import com.dsokolov.kidsplayer.domain.interactor.PlayerInteractor
import com.dsokolov.kidsplayer.domain.model.PlayerEvent
import com.dsokolov.kidsplayer.injector.test.DispatchersProvider
import com.dsokolov.kidsplayer.mvi_core.CommandHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onStart
import com.dsokolov.kidsplayer.mvi.event.PlayerEvent.DomainPlayerEvent as Event
import com.dsokolov.kidsplayer.mvi.command.PlayerCommand as Command

@OptIn(ExperimentalCoroutinesApi::class)
internal class PlayerCommandHandler(
    private val playerInteractor: PlayerInteractor,
) : CommandHandler<Event, Command> {

    private val commandSharedFlow = MutableSharedFlow<Command>()

    override fun getEventSource(): Flow<Event> {
        val commandsFlow = commandSharedFlow.flatMapMerge { command ->
            when (command) {
                is Command.PlayPauseClicked -> playPauseClicked()
                is Command.PageChanged -> pageChanged(command)
                is Command.InitUi -> initUi()
                is Command.RepeatClicked -> repeatClicked()
                is Command.ItemClicked -> itemClicked(command)
                is Command.NextClicked -> nextClicked()
            }
        }

        val playerDataFlow = playerInteractor
            .getPlayerDataFlow
            .map(Event::PlayerDataEvent)
            .flowOn(DispatchersProvider.io())

        val playerSideEffectFlow = playerInteractor
            .pageSideEffectFlow
            .map { Event.ToPage(it.pageNumber) }
            .flowOn(DispatchersProvider.io())

        return listOf(
            playerDataFlow,
            playerSideEffectFlow,
            commandsFlow,
        )
            .merge()
    }

    override suspend fun onCommand(command: Command) {
        commandSharedFlow.emit(command)
    }

    private fun initUi(): Flow<Event> {
        return playerInteractor
            .getPlayerDataFlow
            .map(Event::PlayerDataEvent)
            .onStart {
                playerInteractor.onPlayerEvent(event = PlayerEvent.InitUi)
            }
            .flowOn(DispatchersProvider.io())
    }

    private fun playPauseClicked(): Flow<Event> {
        return flow {
            playerInteractor.onPlayerEvent(event = PlayerEvent.PlayPauseBtnClicked)
        }
    }

    private fun repeatClicked(): Flow<Event> {
        return flow {
            playerInteractor.onPlayerEvent(event = PlayerEvent.RepeatClicked)
        }
    }

    private fun pageChanged(command: Command.PageChanged): Flow<Event> {
        return flow {
            playerInteractor.onPlayerEvent(event = PlayerEvent.PageChanged(command.pageNumber))
        }
    }

    private fun nextClicked(): Flow<Event> {
        return flow {
            playerInteractor.onPlayerEvent(event = PlayerEvent.NextClicked)
        }
    }

    private fun itemClicked(command: Command.ItemClicked): Flow<Event> {
        return flow {
            playerInteractor.onPlayerEvent(event = PlayerEvent.ItemClicked(command.itemId))
        }
    }
}