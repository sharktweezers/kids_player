package com.dsokolov.kidsplayer.domain.di

import com.dsokolov.kidsplayer.domain.interactor.PlayerInteractor
import com.dsokolov.kidsplayer.domain.repository.PlayerRepository
import com.dsokolov.kidsplayer.domain.usecase.GetPlayerDataUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun providePlayerInteractor(): PlayerInteractor {
        return PlayerInteractor()
    }

    @Singleton
    @Provides
    fun provideGetPlayerDataUseCase(
        playerRepository: PlayerRepository
    ): GetPlayerDataUseCase {
        return GetPlayerDataUseCase(playerRepository)
    }
}