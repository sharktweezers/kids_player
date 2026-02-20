package com.dsokolov.kidsplayer

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeLeft
import androidx.compose.ui.test.swipeRight
import com.dsokolov.kidsplayer.resources.R
import com.dsokolov.kidsplayer.ui.HORIZONTAL_PAGER_TAG
import com.dsokolov.kidsplayer.ui.PAGE_INDICATOR_TAG
import com.dsokolov.kidsplayer.ui.PAGE_NUMBER_TAG
import org.junit.Rule
import org.junit.Test

class PlayerUiPagerTest {

    @get:Rule
    internal val rules = createAndroidComposeRule<PlayerActivity>()

    private val pageSize = 4

    @Test
    fun player_ui_pager_swipes() {
        rules
            .onNodeWithTag(HORIZONTAL_PAGER_TAG)
            .assertIsDisplayed()

        rules
            .onNodeWithTag(PAGE_NUMBER_TAG + 0)
            .assertIsDisplayed()

        rules
            .onNodeWithTag(PAGE_NUMBER_TAG + 1)
            .assertIsNotDisplayed()

        for (i in 1 until pageSize) {
            rules
                .onNodeWithTag(HORIZONTAL_PAGER_TAG)
                .performTouchInput {
                    swipeLeft()
                }
            rules.onNodeWithTag(PAGE_NUMBER_TAG + i).assertIsDisplayed()
        }

        for (i in pageSize - 1 downTo 0) {
            rules.onNodeWithTag(PAGE_NUMBER_TAG + i).assertIsDisplayed()
            rules
                .onNodeWithTag(HORIZONTAL_PAGER_TAG)
                .performTouchInput {
                    swipeRight()
                }
        }
    }

    @Test
    fun player_ui_pager_indicator() {
        for (i in pageSize - 1 downTo 0) {
            rules.onNodeWithTag(PAGE_INDICATOR_TAG + i).performClick()
            rules.onNodeWithTag(PAGE_NUMBER_TAG + i).assertIsDisplayed()
            rules.onNodeWithTag(PAGE_INDICATOR_TAG + i)
                .assertContentDescriptionEquals(R.drawable.player_page_active.toString())
        }

        for (i in 0 until pageSize) {
            rules.onNodeWithTag(PAGE_INDICATOR_TAG + i).performClick()
            rules.onNodeWithTag(PAGE_NUMBER_TAG + i).assertIsDisplayed()
            rules.onNodeWithTag(PAGE_INDICATOR_TAG + i)
                .assertContentDescriptionEquals(R.drawable.player_page_active.toString())
        }
    }
}