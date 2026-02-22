package com.dsokolov.kidsplayer.di.internal_modules

import com.dsokolov.kidsplayer.domain.interactor.PlayerInteractor
import com.dsokolov.kidsplayer.player_service.di.PlayerServiceApi
import com.dsokolov.kidsplayer.player_service.di.PlayerServiceComponentHolder
import com.dsokolov.kidsplayer.player_service.di.PlayerServiceDeps
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import javax.inject.Provider

@Module
class PlayerServiceModule {
    @Provides
    @Singleton
    fun providePlayerServiceDependencies(
        playerServiceInteractor: PlayerInteractor,
    ): PlayerServiceDeps = object : PlayerServiceDeps {
        override val playerInteractor: PlayerInteractor
            get() = playerServiceInteractor
    }

    @Singleton
    @Provides
    fun providePlayerServiceComponentHolder(
        dependencies: Provider<PlayerServiceDeps>,
    ): PlayerServiceComponentHolder = PlayerServiceComponentHolder.apply { initDependenciesProvider(dependencies::get) }

    @Provides
    fun providePlayerServiceApi(
        holder: PlayerServiceComponentHolder,
    ): PlayerServiceApi = holder.get()
}