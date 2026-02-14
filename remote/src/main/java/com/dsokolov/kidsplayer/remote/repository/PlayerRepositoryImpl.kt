package com.dsokolov.kidsplayer.remote.repository

import com.dsokolov.kidsplayer.domain.model.PlayableItem
import com.dsokolov.kidsplayer.domain.model.PlayerPage
import com.dsokolov.kidsplayer.domain.repository.PlayerRepository
import com.dsokolov.kidsplayer.remote.store.PlayableItemsStore
import javax.inject.Inject
import kotlin.math.ceil
import kotlin.math.min

class PlayerRepositoryImpl @Inject constructor(
    private val playableItemsStore: PlayableItemsStore,
) : PlayerRepository {

    override fun getPages(): List<PlayerPage> {
        val pages = mutableListOf<PlayerPage>()
        for (page in START_PAGE until getPagesCount()) {
            pages.add(getPage(pageNumber = page, storeItems = playableItemsStore.items))
        }

        return pages
    }

    override fun getPagesCount(): Int {
        return ceil(playableItemsStore.items.size.toFloat() / PAGE_ITEMS).toInt()
    }

    private fun getPage(
        pageNumber: Int,
        storeItems:  List<PlayableItem>,
    ): PlayerPage {
        val items = mutableListOf<PlayableItem>()
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