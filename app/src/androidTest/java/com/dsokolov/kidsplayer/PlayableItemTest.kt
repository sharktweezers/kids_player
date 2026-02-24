package com.dsokolov.kidsplayer

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.dsokolov.kidsplayer.injector.test.DispatchersProvider
import com.dsokolov.kidsplayer.injector.test.TestOnlyVisible
import com.dsokolov.kidsplayer.resources.R
import com.dsokolov.kidsplayer.ui.BUTTON_PLAY_PAUSE
import com.dsokolov.kidsplayer.ui.ITEM_ID_TAG
import com.dsokolov.kidsplayer.ui.PLAYING_ITEM_TAG
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.BeforeClass
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PlayableItemTest {

    @get:Rule
    internal val rules = createAndroidComposeRule<PlayerActivity>()

    companion object {

        @JvmStatic
        @BeforeClass
        @OptIn(TestOnlyVisible::class)
        fun setupClass() {
            // Инициализация, которая выполняется 1 раз для всего класса
            val testDispatcher = UnconfinedTestDispatcher()

            DispatchersProvider.mockDispatchersHolder(
                io = testDispatcher,
                default = testDispatcher,
                main = testDispatcher,
                immediate = testDispatcher,
            )
        }
    }

    @Test
    fun playable_item() = runTest {
        rules
            .onNodeWithTag(ITEM_ID_TAG + 0.toString()).performClick()
        advanceUntilIdle()

        rules
            .onNodeWithTag(BUTTON_PLAY_PAUSE)
            .assertContentDescriptionEquals(R.drawable.pause.toString())
        rules
            .onNodeWithTag(ITEM_ID_TAG + 0.toString())
            .assertContentDescriptionEquals(PLAYING_ITEM_TAG)

        rules
            .onNodeWithTag(BUTTON_PLAY_PAUSE).performClick()
        advanceUntilIdle()

        rules
            .onNodeWithTag(BUTTON_PLAY_PAUSE)
            .assertContentDescriptionEquals(R.drawable.play.toString())
    }
}


