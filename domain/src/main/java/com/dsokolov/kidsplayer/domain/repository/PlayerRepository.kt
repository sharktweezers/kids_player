package com.dsokolov.kidsplayer.domain.repository

import com.dsokolov.kidsplayer.domain.model.PlayerPage

interface PlayerRepository {

    fun getPagesCount(): Int

    fun getPage(pageNumber: Int): PlayerPage

    fun getPages(): List<PlayerPage>
}