package com.dsokolov.kidsplayer.domain.usecase

import com.dsokolov.kidsplayer.domain.model.PlayerPage
import com.dsokolov.kidsplayer.domain.repository.PlayerRepository
import javax.inject.Inject

class GetPlayerPagesUseCases @Inject constructor(
    private val playerRepository: PlayerRepository,
) {
    operator fun invoke(): List<PlayerPage> {
        return playerRepository.getPages()
    }
}