package com.dsokolov.kidsplayer.presentation

import com.dsokolov.kidsplayer.domain.usecase.GetPlayerPagesCountUseCases
import com.dsokolov.kidsplayer.domain.usecase.GetPlayerPagesUseCases
import com.dsokolov.kidsplayer.mvi.PlayerScreenStore
import com.dsokolov.kidsplayer.mvi_core.BaseMviViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayerViewModel @AssistedInject constructor(
    @Assisted isVerticalScreenOrientation: Boolean,
    private val getPlayerPagesCountUseCases: GetPlayerPagesCountUseCases,
    private val getPlayerPagesUseCases: GetPlayerPagesUseCases,
) : BaseMviViewModel() {
    private val playerScreenStore = PlayerScreenStore(getPlayerPagesCountUseCases, getPlayerPagesUseCases)

    private val mutableState = MutableStateFlow(
        playerScreenStore.getInitialPlayerScreenState(isVerticalScreenOrientation)
    )

    val state = mutableState.asStateFlow()

    fun onConfigurationChanged(isVerticalScreenOrientation: Boolean) {
        mutableState.update {
            it.copy(
                isVerticalScreenOrientation = isVerticalScreenOrientation,
                playerPages = PlayerPageMapper.domainToUiPage(getPlayerPagesUseCases(), isVerticalScreenOrientation)
            )
        }
    }

    fun onPageChanged(pageNumber: Int) {
        mutableState.update { it.copy(currentPage = pageNumber,) }
    }

    @AssistedFactory
    interface Factory {
        fun create(isVerticalScreenOrientation: Boolean): PlayerViewModel
    }
}