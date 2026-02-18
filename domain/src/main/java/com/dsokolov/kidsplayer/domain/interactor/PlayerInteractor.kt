package com.dsokolov.kidsplayer.domain.interactor

import com.dsokolov.kidsplayer.domain.model.PlayableItem
import com.dsokolov.kidsplayer.domain.model.PlayerData
import com.dsokolov.kidsplayer.domain.model.PlayerEvent
import com.dsokolov.kidsplayer.domain.model.PlayerPage
import com.dsokolov.kidsplayer.domain.model.PlayerSideEffect
import com.dsokolov.kidsplayer.domain.repository.PlayerRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
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

    private val pageSideEffect = MutableSharedFlow<PlayerSideEffect.ToPage>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    private val serviceSideEffect = MutableSharedFlow<PlayerSideEffect.PlayerServiceSideEffect>(
        replay = 1,
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    val getPlayerDataFlow = playerData.asStateFlow()

    val pageSideEffectFlow = pageSideEffect.asSharedFlow()

    val serviceSideEffectFlow = serviceSideEffect.asSharedFlow()

    suspend fun onPlayerEvent(event: PlayerEvent) {
        mutex.withLock {
            val data = playerData.value

            when (event) {
                is PlayerEvent.OnCreateService -> createService(data)
                is PlayerEvent.PageChanged -> pageChanged(data, event)
                is PlayerEvent.PlayPauseBtnClicked -> playOrPauseClicked(data)
                is PlayerEvent.OnDestroyService -> destroyService(data)
                is PlayerEvent.InitUi -> initUi(data)
                is PlayerEvent.RepeatClicked -> repeatClicked(data)
            }
        }
    }

    private suspend fun initUi(data: PlayerData) {
        if (data.isServiceStarted.not()) {
            playerData.emit(getInitialData())
        } else {
            data.currentItem?.let { currentItem ->
                val pageNumber = getPageNumberByItemId(data.pages, currentItem.id)
                if (pageNumber != data.currentPageNumber) {
                    playerData.emit(data.copy(currentPageNumber = pageNumber))
                }
                pageSideEffect.emit(PlayerSideEffect.ToPage(pageNumber))
            }
        }
    }

    private suspend fun pageChanged(data: PlayerData, event: PlayerEvent.PageChanged) {
        playerData.emit(data.copy(currentPageNumber = event.pageNumber))
    }

    private suspend fun repeatClicked(data: PlayerData) {
        if (data.currentItem != null) {
            val pageNumber = getPageNumberByItemId(data.pages, data.currentItem.id)
            playerData.emit(data.copy(currentPageNumber = pageNumber, isPlay = true))
            pageSideEffect.emit(PlayerSideEffect.ToPage(pageNumber))
            serviceSideEffect.emit(PlayerSideEffect.PlayerServiceSideEffect.Repeat(data.currentItem))
        } else {
            val item = getRandomItem(data.pages)
            val pageNumber = getPageNumberByItemId(data.pages, item.id)
            playerData.emit(data.copy(isPlay = true, currentItem = item, currentPageNumber = pageNumber))
            pageSideEffect.emit(PlayerSideEffect.ToPage(pageNumber))
            serviceSideEffect.emit(PlayerSideEffect.PlayerServiceSideEffect.PlayMediaId(item))
        }
    }

    private suspend fun playOrPauseClicked(data: PlayerData) {
        val isPlay: Boolean = data.isPlay.not()
        when {
            isPlay.not() -> {
                playerData.emit(data.copy(isPlay = isPlay))
                serviceSideEffect.emit(PlayerSideEffect.PlayerServiceSideEffect.Stop)
            }
            data.currentItem == null -> {
                val item = getRandomItem(data.pages)
                val pageNumber = getPageNumberByItemId(data.pages, item.id)
                playerData.emit(data.copy(isPlay = isPlay, currentItem = item, currentPageNumber = pageNumber))
                pageSideEffect.emit(PlayerSideEffect.ToPage(pageNumber))
                serviceSideEffect.emit(PlayerSideEffect.PlayerServiceSideEffect.PlayMediaId(item))
            }
            else -> {
                playerData.emit(data.copy(isPlay = isPlay))
                serviceSideEffect.emit(PlayerSideEffect.PlayerServiceSideEffect.PlayMediaId(data.currentItem))
            }
        }
    }

    private suspend fun createService(data: PlayerData) {
        playerData.emit(data.copy(isServiceStarted = true))
    }

    private suspend fun destroyService(data: PlayerData) {
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