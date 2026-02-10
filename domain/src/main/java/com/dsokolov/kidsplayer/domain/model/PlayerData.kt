package com.dsokolov.kidsplayer.domain.model

data class PlayerData(
    val pagesCount: Int,
    private val playerPages: List<PlayerPage>,
    val currentItemId: Int?,
    val currentPageNumber: Int,
) {
    val pages: List<PlayerPage>
        get() = playerPages.toList()
}