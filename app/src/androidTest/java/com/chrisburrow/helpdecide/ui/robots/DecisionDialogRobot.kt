package com.chrisburrow.helpdecide.ui.robots

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.chrisburrow.helpdecide.ui.views.dialogs.DecisionDialogTags

class DecisionDialogRobot(private val rule: ComposeContentTestRule) {

    init {
        rule.waitUntil {
            rule.onNodeWithTag(DecisionDialogTags.BASE_VIEW_TAG).isDisplayed()
        }
    }

    fun checkText(text: String = "") { rule.onNodeWithTag(DecisionDialogTags.DECISION_TEXT_TAG).assertTextEquals(text) }

    fun pressDone() { rule.onNodeWithTag(DecisionDialogTags.DONE_BUTTON_TAG).performClick() }

    fun pressRemove() { rule.onNodeWithTag(DecisionDialogTags.REMOVE_BUTTON_TAG).performClick() }
}

fun decisionDialog(rule: ComposeContentTestRule, block: DecisionDialogRobot.() -> Unit) = DecisionDialogRobot(rule).apply(block)