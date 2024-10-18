package com.chrisburrow.helpdecide.ui.robots

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsFocused
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performImeAction
import androidx.compose.ui.test.performTextInput
import com.chrisburrow.helpdecide.ui.views.dialogs.AddDialogTags
class AddDialogRobot(private val rule: ComposeContentTestRule) {

    init {
        rule.waitUntil {
            rule.onNodeWithTag(AddDialogTags.BASE_VIEW_TAG).isDisplayed()
        }
    }

    fun isSaveEnabled() { rule.onNodeWithTag(AddDialogTags.SAVE_BUTTON_TAG).assertIsEnabled() }

    fun isSaveDisabled() { rule.onNodeWithTag(AddDialogTags.SAVE_BUTTON_TAG).assertIsNotEnabled() }

    fun isClearEnabled() { rule.onNodeWithTag(AddDialogTags.CLEAR_BUTTON_TAG).assertIsEnabled() }

    fun isClearDisabled() { rule.onNodeWithTag(AddDialogTags.CLEAR_BUTTON_TAG).assertIsNotEnabled() }

    fun isTextFocused() { rule.onNodeWithTag(AddDialogTags.OPTION_TEXT_TAG).assertIsFocused() }

    fun checkText(text: String = "") { rule.onNodeWithTag(AddDialogTags.OPTION_TEXT_TAG).assertTextEquals(text) }

    fun pressSave() { rule.onNodeWithTag(AddDialogTags.SAVE_BUTTON_TAG).performClick() }

    fun pressCancel() { rule.onNodeWithTag(AddDialogTags.CANCEL_BUTTON_TAG).performClick() }

    fun pressClear() { rule.onNodeWithTag(AddDialogTags.CLEAR_BUTTON_TAG).performClick() }

    fun typeText(text: String) { rule.onNodeWithTag(AddDialogTags.OPTION_TEXT_TAG).performTextInput(text) }

    fun pressKeyboardDone() { rule.onNodeWithTag(AddDialogTags.OPTION_TEXT_TAG).performImeAction() }

    fun dialogHidden() { rule.onNodeWithTag(AddDialogTags.BASE_VIEW_TAG).assertDoesNotExist() }
}


fun addDialog(rule: ComposeContentTestRule, block: AddDialogRobot.() -> Unit) = AddDialogRobot(rule).apply(block)