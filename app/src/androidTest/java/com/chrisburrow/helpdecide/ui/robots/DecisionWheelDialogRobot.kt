package com.chrisburrow.helpdecide.ui.robots

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.chrisburrow.helpdecide.ui.views.dialogs.DecisionDialogTags
import com.chrisburrow.helpdecide.ui.views.screens.DecideWheelTags

class DecisionWheelDialogRobot(private val rule: ComposeContentTestRule) {

    init {
        rule.waitUntil {
            rule.onNodeWithTag(DecideWheelTags.BASE_VIEW_TAG).isDisplayed()
        }
    }

    fun checkText(text: String = "") { rule.onNodeWithTag(DecideWheelTags.DECIDED_TEXT_TAG).assertTextEquals(text) }

    fun pressDone() { rule.onNodeWithTag(DecideWheelTags.DONE_BUTTON_TAG).performClick() }

    fun pressRemove() { rule.onNodeWithTag(DecideWheelTags.REMOVE_BUTTON_TAG).performClick() }
}

fun decisionWheel(rule: ComposeContentTestRule, block: DecisionWheelDialogRobot.() -> Unit) = DecisionWheelDialogRobot(rule).apply(block)