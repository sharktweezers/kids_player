package com.dsokolov.kidsplayer.domain.model

data class PlayerData(
    val pagesCount: Int,
    val currentItemId: Int?,
    val currentPageNumber: Int,
    val isPlay: Boolean,
    private val playerPages: List<PlayerPage>,
) {
    val pages: List<PlayerPage>
        get() = playerPages.toList()
}