package com.dsokolov.kidsplayer.domain.repository

import com.dsokolov.kidsplayer.domain.model.PlayerPage
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    fun getPages(): Flow<List<PlayerPage>>

    fun getCurrentPageNumber(): Flow<Int>

    fun getCurrentItem(): Flow<Int?>
}