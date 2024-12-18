package com.chrisburrow.helpdecide.ui.robots

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.chrisburrow.helpdecide.ui.views.screens.HomeTags
import com.chrisburrow.helpdecide.ui.views.screens.options.OptionListTags

class HomeRobot(private val rule: ComposeContentTestRule) {

    init {

        rule.waitUntil {
            rule.onNodeWithTag(HomeTags.BASE_VIEW_TAG).isDisplayed()
        }
    }

    fun pressAdd() { rule.onNodeWithTag(HomeTags.ADD_TEXT_TAG).performClick() }

    fun pressDelete(position: Int) {

        rule.onNodeWithTag(OptionListTags.DELETE_TAG + position).performClick()
    }

    fun pressClearAll() { rule.onNodeWithTag(HomeTags.CLEAR_ALL_TAG).performClick() }

    fun pressSettings() { rule.onNodeWithTag(HomeTags.SETTINGS_TAG).performClick() }

    fun checkNumberOfOptions(size: Int) {

        rule.onAllNodesWithTag(OptionListTags.BASE_VIEW).assertCountEquals(size)
    }

    fun pressDecide() { rule.onNodeWithTag(HomeTags.DECIDE_BUTTON_TAG).performClick() }

    fun checkDecideEnabled() { rule.onNodeWithTag(HomeTags.DECIDE_BUTTON_TAG).assertIsEnabled() }

    fun checkDecideDisabled() { rule.onNodeWithTag(HomeTags.DECIDE_BUTTON_TAG).assertIsNotEnabled() }

    fun checkClearAllShown() { rule.onNodeWithTag(HomeTags.CLEAR_ALL_TAG).assertIsDisplayed() }

    fun checkClearAllHidden() { rule.onNodeWithTag(HomeTags.CLEAR_ALL_TAG).assertDoesNotExist() }

    fun checkEmptyShown() { rule.onNodeWithTag(HomeTags.EMPTY_VIEW_TAG).assertIsDisplayed() }

    fun checkAddByVoiceShown() { rule.onNodeWithTag(HomeTags.ADD_VOICE_TAG).assertIsDisplayed() }

    fun checkAddByVoiceHidden() { rule.onNodeWithTag(HomeTags.ADD_VOICE_TAG).assertDoesNotExist() }

    fun checkOptionShown(position: Int, text: String) {

        rule.onNodeWithTag(OptionListTags.TEXT_TAG + position)
            .assertTextContains(text)
    }
}

fun home(rule: ComposeContentTestRule, block: HomeRobot.() -> Unit) = HomeRobot(rule).apply(block)