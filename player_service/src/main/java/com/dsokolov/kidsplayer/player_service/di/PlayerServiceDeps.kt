package com.dsokolov.kidsplayer.player_service.di

import com.dsokolov.kidsplayer.domain.interactor.PlayerInteractor
import com.dsokolov.kidsplayer.injector.ComponentDependencies
import com.dsokolov.kidsplayer.utils.DispatchersProvider

interface PlayerServiceDeps : ComponentDependencies {
    val playerInteractor: PlayerInteractor
    val dispatchersProvider: DispatchersProvider
}