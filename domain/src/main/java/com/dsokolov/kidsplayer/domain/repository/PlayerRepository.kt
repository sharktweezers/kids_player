package com.dsokolov.kidsplayer.domain.repository

import com.dsokolov.kidsplayer.domain.model.PlayerPage

interface PlayerRepository {

    fun getPages(): List<PlayerPage>

    fun getPagesCount(): Int
}