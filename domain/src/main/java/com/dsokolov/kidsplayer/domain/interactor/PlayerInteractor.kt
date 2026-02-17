package com.dsokolov.kidsplayer.domain.interactor

import com.dsokolov.kidsplayer.domain.model.PlayableItem
import com.dsokolov.kidsplayer.domain.model.PlayerData
import com.dsokolov.kidsplayer.domain.model.PlayerEvent
import com.dsokolov.kidsplayer.domain.model.PlayerPage
import com.dsokolov.kidsplayer.domain.model.PlayerSideEffect
import com.dsokolov.kidsplayer.domain.repository.PlayerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
class PlayerInteractor internal constructor(
    private val playerRepository: PlayerRepository,
) {

    private val mutex = Mutex()

    private val playerData = MutableStateFlow(getInitialData())

    private val sideEffect = MutableSharedFlow<PlayerSideEffect>(
        replay = 4,
        extraBufferCapacity = 4,
    )

    val getPlayerDataFlow = playerData.asStateFlow()

    val getPlayerSideEffectFlow = sideEffect.asSharedFlow()

    suspend fun onPlayerEvent(event: PlayerEvent) {
        mutex.withLock {
            val data = playerData.value

            when (event) {
                is PlayerEvent.OnCreateService -> createService(data)
                is PlayerEvent.PageChanged -> pageChanged(data, event)
                is PlayerEvent.PlayPauseBtnClicked -> playOrPauseClicked(data)
                is PlayerEvent.OnDestroyService -> destroyService(data)
                is PlayerEvent.InitUi -> initUi(data)
            }
        }
        /*playerData.update { data ->
            when (event) {
                is PlayerEvent.PageChanged -> pageChanged(data, event)
                is PlayerEvent.PlayBtnClicked -> playOrPauseClicked(data)

                is PlayerEvent.PlayableItemChanged -> {
                    val itemId = event.itemId
                    val pageNumber = getPageNumberByItemId(data.pages, itemId)
                    data.copy(
                        currentItem = getItemById(data.pages, itemId),
                        currentPageNumber = pageNumber,
                    )
                }

                is PlayerEvent.Stop -> {
                    data.copy(isPlay = false)
                }

                is PlayerEvent.PlayingItemPage -> {
                    val currentItem = data.currentItem
                    if (currentItem == null) {
                        data
                    } else {
                        val pageNumber = getPageNumberByItemId(data.pages, currentItem.id)
                        data.copy(currentPageNumber = pageNumber)
                    }
                }
            }
        }*/
    }

    private suspend fun initUi(data: PlayerData) {
        if (data.isServiceStarted.not()) {
            playerData.emit(getInitialData())
        }
    }

    private suspend fun pageChanged(data: PlayerData, event: PlayerEvent.PageChanged) {
        playerData.emit(data.copy(currentPageNumber = event.pageNumber))
    }

    private suspend fun playOrPauseClicked(data: PlayerData) {
        val isPlay: Boolean = data.isPlay.not()
        when {
            isPlay.not() -> {
                playerData.emit(data.copy(isPlay = isPlay))
                sideEffect.emit(PlayerSideEffect.Stop)
            }
            data.currentItem == null -> {
                val item = getRandomItem(data.pages)
                val pageNumber = getPageNumberByItemId(data.pages, item.id)
                playerData.emit(data.copy(isPlay = isPlay, currentItem = item, currentPageNumber = pageNumber))
                sideEffect.emit(PlayerSideEffect.ToPage(pageNumber))
                sideEffect.emit(PlayerSideEffect.PlayMediaId(item))
            }
            else -> {
                val item = getRandomItem(data.pages)
                playerData.emit(data.copy(isPlay = isPlay))
                sideEffect.emit(PlayerSideEffect.PlayMediaId(item))
            }
        }
    }

    private suspend fun createService(data: PlayerData) {
        playerData.emit(data.copy(isServiceStarted = true))
    }

    private suspend fun destroyService(data: PlayerData) {
        sideEffect.resetReplayCache()
        playerData.emit(data.copy(isPlay = false, isServiceStarted = false))
    }

    private fun getRandomItem(pages: List<PlayerPage>): PlayableItem {
        val items = mutableListOf<PlayableItem>()
        pages.forEach { page ->
            items += page.items
        }

        val randomIndex = Random(System.currentTimeMillis()).nextInt(
            from = 0,
            until = items.size
        )

        return items[randomIndex]
    }

    private fun getPageNumberByItemId(pages: List<PlayerPage>, itemId: Int): Int {
        pages.forEachIndexed { index, page ->
            if (page.items.find { it.id == itemId } != null) return index
        }

        return 0
    }

    private fun getItemById(pages: List<PlayerPage>, itemId: Int): PlayableItem? {
        pages.forEach { page ->
            page.items.forEach { item ->
                if (item.id == itemId) return item
            }
        }

        return null
    }

    private fun getInitialData(): PlayerData {
        return PlayerData(
            pagesCount = playerRepository.getPagesCount(),
            currentItem = null,
            currentPageNumber = 0,
            isPlay = false,
            playerPages = playerRepository.getPages(),
            isServiceStarted = false
        )
    }
}