package com.dsokolov.kidsplayer.domain.interactor

import com.dsokolov.kidsplayer.domain.model.PlayerData
import com.dsokolov.kidsplayer.domain.model.PlayerEvent
import com.dsokolov.kidsplayer.domain.model.PlayerPage
import com.dsokolov.kidsplayer.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    val getPlayerDataFlow = playerData.asStateFlow()

    fun onPlayerEventChanged(event: PlayerEvent) {
        playerData.update { data ->
            when (event) {
                is PlayerEvent.PageChanged -> data.copy(currentPageNumber = event.page)
                is PlayerEvent.PlayBtnClicked -> data.copy(isPlay = data.isPlay.not())
                is PlayerEvent.PlayableItemChanged -> {
                    val itemId = event.itemId
                    val pageNumber = getPageNumberByItemId(data.pages, itemId)
                    if (pageNumber == null) {
                        data.copy(currentItemId = itemId)
                    } else {
                        data.copy(
                            currentItemId = itemId,
                            currentPageNumber = pageNumber,
                        )
                    }
                }

                is PlayerEvent.Stop -> {
                    data.copy(isPlay = false)
                }

                is PlayerEvent.PlayingItemPage -> {
                    val currentItem = data.currentItemId
                    if (currentItem == null) {
                        data
                    } else {
                        val pageNumber = getPageNumberByItemId(data.pages, currentItem)
                        if (pageNumber == null) {
                            data
                        } else {
                            data.copy(currentPageNumber = pageNumber)
                        }
                    }
                }
            }
        }
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