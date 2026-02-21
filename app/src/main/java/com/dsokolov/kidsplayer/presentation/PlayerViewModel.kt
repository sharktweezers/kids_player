package com.dsokolov.kidsplayer.presentation

import androidx.lifecycle.viewModelScope
import com.dsokolov.kidsplayer.mvi.event.PlayerEvent
import com.dsokolov.kidsplayer.mvi.factory.PlayerStoreFactory
import com.dsokolov.kidsplayer.mvi.mapper.PlayerDomainToUiSideEffectMapper
import com.dsokolov.kidsplayer.mvi.mapper.PlayerDomainToUiStateMapper
import com.dsokolov.kidsplayer.mvi_core.BaseMviViewModel
import com.dsokolov.kidsplayer.utils.DispatchersProvider
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

internal class PlayerViewModel @AssistedInject constructor(
    @Assisted isVerticalScreenOrientation: Boolean,
    playerStoreFactory: PlayerStoreFactory,
    playerDomainToUiStateMapper: PlayerDomainToUiStateMapper,
    playerDomainToUiSideEffectMapper: PlayerDomainToUiSideEffectMapper,
    private val dispatchersProvider: DispatchersProvider,
) : BaseMviViewModel() {
    private val stateFlow = MutableStateFlow<UiPlayerState>(
        UiPlayerState.UiPlayerLoading
    )

    private val sideEffectFlow = MutableSharedFlow<PlayerUiSideEffect>()

    val state = stateFlow.asStateFlow()

    internal val sideEffect = sideEffectFlow.asSharedFlow()

    private val uiEvent = MutableSharedFlow<PlayerEvent.UiPlayerEvent>()

    init {
        val mviStore = playerStoreFactory.createStore(isVerticalScreenOrientation)

        mviStore.start(
            coroutineScope = viewModelScope,
            coroutineDispatcher = dispatchersProvider.default,
            actionState = { state ->
                viewModelScope.launch(dispatchersProvider.immediate) {
                    stateFlow.emit(playerDomainToUiStateMapper.map(state))
                }
            },
            actionSideEffect = { mviSideEffect ->
                val uiSideEffect = playerDomainToUiSideEffectMapper.map(mviSideEffect = mviSideEffect)
                sideEffectFlow.emit(uiSideEffect)
            },
        )

        val uiEvents = merge(
            uiEvent,
        )

        uiEvents.onEach(mviStore::onEvent)
            .flowOn(dispatchersProvider.default)
            .launchIn(viewModelScope)
    }

    fun onConfigurationChanged(isVerticalScreenOrientation: Boolean) = launchUnit(dispatchersProvider.main) {
        uiEvent.emit(
            PlayerEvent.UiPlayerEvent.ScreenOrientationChanged(
                isVerticalScreenOrientation
            )
        )
    }

    fun onPageChanged(pageNumber: Int) = launchUnit(dispatchersProvider.main) {
        uiEvent.emit(PlayerEvent.UiPlayerEvent.PageChanged(pageNumber))
    }

    fun repeatClicked() = launchUnit(dispatchersProvider.main) {
        uiEvent.emit(PlayerEvent.UiPlayerEvent.RepeatClicked)
    }

    fun playPauseClicked() = launchUnit(dispatchersProvider.main) {
        uiEvent.emit(PlayerEvent.UiPlayerEvent.PlayPauseClicked)
    }

    fun nextClicked() = launchUnit(dispatchersProvider.main) {
        uiEvent.emit(PlayerEvent.UiPlayerEvent.NextClicked)
    }

    fun onItemClick(itemId: Int) = launchUnit(dispatchersProvider.main) {
        uiEvent.emit(PlayerEvent.UiPlayerEvent.ItemClicked(itemId))
    }

    @AssistedFactory
    interface Factory {
        fun create(isVerticalScreenOrientation: Boolean): PlayerViewModel
    }
}