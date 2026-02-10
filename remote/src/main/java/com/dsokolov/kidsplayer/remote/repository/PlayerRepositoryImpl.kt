package com.dsokolov.kidsplayer.remote.repository

import com.dsokolov.kidsplayer.domain.model.PlayableItem
import com.dsokolov.kidsplayer.domain.model.PlayerPage
import com.dsokolov.kidsplayer.domain.repository.PlayerRepository
import com.dsokolov.kidsplayer.remote.store.PlayableItemsStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.min

class PlayerRepositoryImpl @Inject constructor(
    private val playableItemsStore: PlayableItemsStore,
) : PlayerRepository {

    override fun getPages(): Flow<List<PlayerPage>> {
        return playableItemsStore.items
            .map { items ->
                val pages = mutableListOf<PlayerPage>()
                for (page in START_PAGE until getPagesCount(items.size)) {
                    pages.add(getPage(pageNumber = page))
                }
                pages.toList()
            }
    }

    override fun getCurrentPageNumber(): Flow<Int> {
        return playableItemsStore.getCurrentPageNumberFlow()
    }

    override fun getCurrentItem(): Flow<Int?> {
        return playableItemsStore.getCurrentItemFlow()
    }

    private fun getPagesCount(size: Int): Int {
        return ceil(size.toFloat() / PAGE_ITEMS).toInt()
    }

    private fun getPage(pageNumber: Int): PlayerPage {
        val items = mutableListOf<PlayableItem>()
        val storeItems = playableItemsStore.items.value
        val startIndex = pageNumber * PAGE_ITEMS
        val endIndex = min(startIndex + PAGE_ITEMS, storeItems.size)

        for (i in startIndex until endIndex) {
            items.add(storeItems[i])
        }

        return PlayerPage(
            number = pageNumber,
            playableItems = items,
        )
    }

    companion object {
        private const val START_PAGE = 0
        private const val PAGE_ITEMS = 12
    }
}