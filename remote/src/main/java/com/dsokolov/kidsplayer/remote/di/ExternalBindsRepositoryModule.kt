package com.dsokolov.kidsplayer.remote.di

import com.dsokolov.kidsplayer.domain.repository.PlayerRepository
import com.dsokolov.kidsplayer.remote.repository.PlayerRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
interface ExternalBindsRepositoryModule {
    @Binds
    fun bindPlayerRepository(playerRepository: PlayerRepositoryImpl): PlayerRepository
}