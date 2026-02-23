package com.dsokolov.kidsplayer

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.dsokolov.kidsplayer.injector.test.DispatchersProvider
import com.dsokolov.kidsplayer.injector.test.TestOnlyVisible
import com.dsokolov.kidsplayer.resources.R
import com.dsokolov.kidsplayer.ui.BUTTON_PLAY_PAUSE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlayerUiUserActionsTest {

    @get:Rule
    internal val rules = createAndroidComposeRule<PlayerActivity>()

    private val standardTestDispatcher = StandardTestDispatcher()

    @OptIn(TestOnlyVisible::class)
    @Before
    fun setup() {
        DispatchersProvider.mockDispatchersHolder(
            io = standardTestDispatcher,
            default = standardTestDispatcher,
            main = Dispatchers.Main,
            immediate = Dispatchers.Main,
        )
    }

    @OptIn(TestOnlyVisible::class)
    @Test
    fun play_pause() = runTest(standardTestDispatcher) {
        rules
            .onNodeWithTag(BUTTON_PLAY_PAUSE).performClick()
        advanceUntilIdle()
        rules
            .onNodeWithTag(BUTTON_PLAY_PAUSE)
            .assertContentDescriptionEquals(R.drawable.pause.toString())

        rules
            .onNodeWithTag(BUTTON_PLAY_PAUSE).performClick()
        advanceUntilIdle()
        rules
            .onNodeWithTag(BUTTON_PLAY_PAUSE)
            .assertContentDescriptionEquals(R.drawable.play.toString())

    }
}


