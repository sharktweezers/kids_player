package com.dsokolov.kidsplayer.mvi.reducer

import com.dsokolov.kidsplayer.domain.model.PlayerData
import com.dsokolov.kidsplayer.mvi.event.PlayerEvent.DomainPlayerEvent
import com.dsokolov.kidsplayer.mvi.side_effect.PlayerSideEffect
import com.dsokolov.kidsplayer.mvi.state.PlayerScreenState
import org.junit.Test
import org.mockito.kotlin.spy

class PlayerDomainReducerTest {

    private val reducer = spy(PlayerDomainReducer())

    private val initState = PlayerScreenState(
        isVerticalScreenOrientation = true,
        playerData = null
    )

    @Test
    fun reduceToPageTest() {
        val eventPageNumber = 10
        val update = reducer.update(
            state = initState,
            event = DomainPlayerEvent.ToPage(eventPageNumber),
        )

        val toPageSideEffect = update.sideEffects?.getOrNull(0) as? PlayerSideEffect.ToPage
        assert(toPageSideEffect != null)
        toPageSideEffect?.let { toPage ->
            assert(toPage.pageNumber == eventPageNumber)
        }
    }

    @Test
    fun reducePlayerDataTest() {
        val playerData = PlayerData(
            pagesCount = 0,
            currentItem = null,
            currentPageNumber = 0,
            isPlay = true,
            playerPages = emptyList(),
            isServiceStarted = false,
        )

        val update = reducer.update(
            state = initState,
            event = DomainPlayerEvent.PlayerDataEvent(playerData),
        )

        val updatedState = update.state
        assert(updatedState != null)

        updatedState?.let { state ->
            assert(state.playerData == playerData)
        }

        val startPlayerServiceSideEffect = update.sideEffects?.getOrNull(0) as? PlayerSideEffect.StartPlayerService
        assert(startPlayerServiceSideEffect != null)
    }
}