package com.chrisburrow.helpdecide.ui.robots

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.chrisburrow.helpdecide.ui.views.dialogs.DecisionDialogTags
import com.chrisburrow.helpdecide.ui.views.dialogs.GeneralDialogTags

class GeneralDialogRobot(private val rule: ComposeContentTestRule) {

    init {
        rule.onNodeWithTag(GeneralDialogTags.BASE_VIEW_TAG).assertIsDisplayed()
    }

    fun checkDescription(text: String = "") { rule.onNodeWithTag(GeneralDialogTags.DESCRIPTION_TAG).assertTextEquals(text) }

    fun checkConfirm(text: String = "") { rule.onNodeWithText(text).isDisplayed() }

    fun pressConfirm() { rule.onNodeWithTag(GeneralDialogTags.CONFIRM_VIEW_TAG).performClick() }

    fun checkCancel(text: String = "") { rule.onNodeWithText(text).isDisplayed() }

    fun pressCancel() { rule.onNodeWithTag(GeneralDialogTags.CANCEL_VIEW_TAG).performClick() }
}

fun generalDialog(rule: ComposeContentTestRule, block: GeneralDialogRobot.() -> Unit) = GeneralDialogRobot(rule).apply(block)