package com.dsokolov.kidsplayer.domain.model

data class PlayerPage(
    val number: Int,
    private val playableItems: List<PlayableItem>,
) {
    val items: List<PlayableItem>
        get() = playableItems.toList()
}