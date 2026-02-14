package com.dsokolov.kidsplayer.domain.di

import com.dsokolov.kidsplayer.domain.interactor.PlayerInteractor
import com.dsokolov.kidsplayer.domain.repository.PlayerRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun providePlayerInteractor(
        playerRepository: PlayerRepository,
    ): PlayerInteractor {
        return PlayerInteractor(playerRepository)
    }
}