package com.dsokolov.kidsplayer.presentation

import androidx.lifecycle.viewModelScope
import com.dsokolov.kidsplayer.mvi.event.PlayerEvent
import com.dsokolov.kidsplayer.mvi.factory.PlayerStoreFactory
import com.dsokolov.kidsplayer.mvi.mapper.PlayerDomainToUiSideEffectMapper
import com.dsokolov.kidsplayer.mvi.mapper.PlayerDomainToUiStateMapper
import com.dsokolov.kidsplayer.mvi_core.BaseMviViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class PlayerViewModel @AssistedInject constructor(
    @Assisted isVerticalScreenOrientation: Boolean,
    playerStoreFactory: PlayerStoreFactory,
    playerDomainToUiStateMapper: PlayerDomainToUiStateMapper,
    playerDomainToUiSideEffectMapper: PlayerDomainToUiSideEffectMapper,
) : BaseMviViewModel() {
    private val stateFlow = MutableStateFlow<UiPlayerState>(
        UiPlayerState.UiPlayerLoading
    )

    val state = stateFlow.asStateFlow()

    private val uiEvent = MutableSharedFlow<PlayerEvent.UiPlayerEvent>()

    init {
        val mviStore = playerStoreFactory.createStore(isVerticalScreenOrientation)

        mviStore.start(
            coroutineScope = viewModelScope,
            actionState = { state ->
                viewModelScope.launch(Dispatchers.Main.immediate) {
                    stateFlow.emit(playerDomainToUiStateMapper.map(state))
                }
            },
            actionSideEffect = { mviSideEffect ->
                //val uiSideEffect = loanCalculatorUiSideEffectMapper.map(mviSideEffect = mviSideEffect)
                //_sideEffectFlow.emit(uiSideEffect)
            },
        )

        val uiEvents = merge(
            uiEvent,
        )

        uiEvents.onEach(mviStore::onEvent)
            .flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    fun onConfigurationChanged(isVerticalScreenOrientation: Boolean) {

    }

    fun onPageChanged(pageNumber: Int) {

    }

    /*private val mutableState = MutableStateFlow(
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
    }*/

    @AssistedFactory
    interface Factory {
        fun create(isVerticalScreenOrientation: Boolean): PlayerViewModel
    }
}