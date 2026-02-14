package com.dsokolov.kidsplayer.domain.interactor

import com.dsokolov.kidsplayer.domain.model.PlayerData
import com.dsokolov.kidsplayer.domain.model.PlayerEvent
import com.dsokolov.kidsplayer.domain.model.PlayerEventType
import com.dsokolov.kidsplayer.domain.model.PlayerPage
import com.dsokolov.kidsplayer.domain.repository.PlayerRepository
import com.dsokolov.kidsplayer.utils.flow.throttleFirst
import com.dsokolov.kidsplayer.utils.flow.withLatestFrom
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class PlayerInteractor internal constructor(
    playerRepository: PlayerRepository,
) {

    private val playerData = MutableStateFlow(
        PlayerData(
            pagesCount = playerRepository.getPagesCount(),
            currentItemId = null,
            currentPageNumber = 0,
            isPlay = false,
            playerPages = playerRepository.getPages(),
        )
    )

    private val playerEvent = MutableStateFlow(PlayerEvent(null))

    fun getPlayerDataFlow(): Flow<PlayerData> {
        return playerEvent
            .throttleFirst(windowDuration = THROTTLE_WINDOW)
            .withLatestFrom(playerData) { event, data ->
                when (val type = event.eventType) {
                    is PlayerEventType.PlayableItemChanged -> {
                        val pageNumber = getPageNumberByItemId(data.pages, type.itemId)
                        if (pageNumber == null) {
                            data.copy(currentItemId = type.itemId)
                        } else {
                            data.copy(
                                currentItemId = type.itemId,
                                currentPageNumber = pageNumber,
                            )
                        }
                    }
                    is PlayerEventType.PageChanged -> data.copy(currentPageNumber = type.page)
                    is PlayerEventType.PlayBtnClicked -> data.copy(isPlay = data.isPlay.not())
                    null -> data
                }
            }
            .onEach { updatedPlayerData ->
                playerData.update { updatedPlayerData }
            }
    }

    fun onPlayerEventChanged(eventType: PlayerEventType) {
        playerEvent.update { it.copy(eventType = eventType) }
    }

    private fun getPageNumberByItemId(pages: List<PlayerPage>, itemId: Int): Int? {
        pages.forEachIndexed { index, page ->
            if (page.items.find { it.id == itemId } != null) return index
        }

        return null
    }

    private companion object {
        const val THROTTLE_WINDOW = 50L
    }
}