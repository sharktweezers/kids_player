package com.dsokolov.kidsplayer.domain.usecase

import com.dsokolov.kidsplayer.domain.repository.PlayerRepository
import javax.inject.Inject

class GetPlayerPagesCountUseCases @Inject constructor(
    private val playerRepository: PlayerRepository,
) {
    operator fun invoke(): Int {
        return playerRepository.getPagesCount()
    }
}