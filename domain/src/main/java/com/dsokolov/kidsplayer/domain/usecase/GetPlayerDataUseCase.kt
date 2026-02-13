package com.dsokolov.kidsplayer.domain.usecase

import com.dsokolov.kidsplayer.domain.model.PlayerData
import com.dsokolov.kidsplayer.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetPlayerDataUseCase internal constructor(
    private val playerRepository: PlayerRepository,
) {
    operator fun invoke(): Flow<PlayerData> {
        return combine(
            playerRepository.getPages(),
            playerRepository.getCurrentPageNumber(),
            playerRepository.getCurrentItem(),
        ) { pages, pageNumber, currentItem ->
            PlayerData(
                pagesCount = pages.size,
                playerPages = pages,
                currentItemId = currentItem,
                currentPageNumber = pageNumber,
            )
        }
    }
}