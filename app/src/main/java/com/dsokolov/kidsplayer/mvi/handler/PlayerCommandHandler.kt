package com.dsokolov.kidsplayer.mvi.handler

import com.dsokolov.kidsplayer.domain.interactor.PlayerInteractor
import com.dsokolov.kidsplayer.mvi_core.DefaultCommandHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import com.dsokolov.kidsplayer.mvi.event.PlayerEvent.DomainPlayerEvent as Event
import com.dsokolov.kidsplayer.mvi.command.PlayerCommand as Command

@OptIn(ExperimentalCoroutinesApi::class)
class PlayerCommandHandler(
    private val playerInteractor: PlayerInteractor
) : DefaultCommandHandler<Event, Command>() {

    override fun handleCommand(command: Command): Flow<Event> {
        return when (command) {
            is Command.GetPlayerData -> getPlayerData()
        }
    }

    private fun getPlayerData(): Flow<Event> {
        return playerInteractor
            .getPlayerDataFlow()
            .map(Event::PlayerDataEvent)
            .flowOn(Dispatchers.IO)
    }
}