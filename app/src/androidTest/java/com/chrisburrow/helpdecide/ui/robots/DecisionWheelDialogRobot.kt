package com.chrisburrow.helpdecide.ui.robots

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.chrisburrow.helpdecide.ui.views.dialogs.DecideWheelDialogTags
import com.chrisburrow.helpdecide.ui.views.dialogs.DecisionDialogTags

class DecisionWheelDialogRobot(private val rule: ComposeContentTestRule) {

    init {
        rule.onNodeWithTag(DecideWheelDialogTags.BASE_VIEW_TAG).assertIsDisplayed()
    }

    fun checkText(text: String = "") { rule.onNodeWithTag(DecideWheelDialogTags.DECISION_TEXT_TAG).assertTextEquals(text) }

    fun pressDone() { rule.onNodeWithTag(DecideWheelDialogTags.DONE_BUTTON_TAG).performClick() }

    fun pressClear() { rule.onNodeWithTag(DecideWheelDialogTags.CLEAR_BUTTON_TAG).performClick() }
}

fun decisionWheel(rule: ComposeContentTestRule, block: DecisionWheelDialogRobot.() -> Unit) = DecisionWheelDialogRobot(rule).apply(block)