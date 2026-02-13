package com.dsokolov.kidsplayer.domain.interactor

import kotlinx.coroutines.flow.MutableStateFlow

class PlayerInteractor internal constructor() {

    private val currentItemFlow = MutableStateFlow<Int?>(null)

    private val currentPageFlow = MutableStateFlow(0)

    private val isPlayStatusFlow = MutableStateFlow(false)
}