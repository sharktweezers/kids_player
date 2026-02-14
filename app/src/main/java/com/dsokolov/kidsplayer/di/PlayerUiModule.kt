package com.dsokolov.kidsplayer.di

import com.dsokolov.kidsplayer.domain.interactor.PlayerInteractor
import com.dsokolov.kidsplayer.mvi.factory.PlayerStoreFactory
import com.dsokolov.kidsplayer.mvi.handler.PlayerCommandHandler
import com.dsokolov.kidsplayer.mvi.mapper.PlayerDomainToUiSideEffectMapper
import com.dsokolov.kidsplayer.mvi.mapper.PlayerDomainToUiStateMapper
import com.dsokolov.kidsplayer.mvi.reducer.PlayerDomainReducer
import com.dsokolov.kidsplayer.mvi.reducer.PlayerReducer
import com.dsokolov.kidsplayer.mvi.reducer.PlayerUiReducer
import dagger.Module
import dagger.Provides

@Module
internal class PlayerUiModule {

    @Provides
    fun providePlayerUiReducer() = PlayerUiReducer()

    @Provides
    fun providePlayerDomainReducer() = PlayerDomainReducer()

    @Provides
    fun providePlayerReducer(
        playerUiReducer: PlayerUiReducer,
        playerDomainReducer: PlayerDomainReducer,
    ) = PlayerReducer(playerUiReducer, playerDomainReducer)

    @Provides
    fun providePlayerCommandHandler(
        playerInteractor: PlayerInteractor
    ) = PlayerCommandHandler(playerInteractor = playerInteractor)

    @Provides
    fun providePlayerStoreFactory(
        reducer: PlayerReducer,
        commandHandler: PlayerCommandHandler,
    ) = PlayerStoreFactory(reducer, commandHandler)

    @Provides
    fun providePlayerDomainToUiStateMapper() = PlayerDomainToUiStateMapper()

    @Provides
    fun providePlayerDomainToUiSideEffectMapper() = PlayerDomainToUiSideEffectMapper()
}