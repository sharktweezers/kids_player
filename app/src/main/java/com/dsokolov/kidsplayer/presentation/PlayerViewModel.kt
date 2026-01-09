package com.dsokolov.kidsplayer.presentation

import com.dsokolov.kidsplayer.mvi.PlayerScreenStore
import com.dsokolov.kidsplayer.mvi_core.BaseMviViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PlayerViewModel @AssistedInject constructor(
    @Assisted isVerticalScreenOrientation: Boolean,
) : BaseMviViewModel() {
    private val playerScreenStore = PlayerScreenStore()

    private val mutableState = MutableStateFlow(
        playerScreenStore.getInitialPlayerScreenState(isVerticalScreenOrientation)
    )

    val state = mutableState.asStateFlow()

    @AssistedFactory
    interface Factory {
        fun create(isVerticalScreenOrientation: Boolean): PlayerViewModel
    }
}