package com.chrisburrow.helpdecide.ui.robots

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.chrisburrow.helpdecide.ui.views.dialogs.DecisionDefaultDialogTags

class DecisionDefaultDialogRobot(private val rule: ComposeContentTestRule) {

    init {
        rule.waitUntil {
            rule.onNodeWithTag(DecisionDefaultDialogTags.BASE_VIEW_TAG).isDisplayed()
        }
    }

    fun pressWheelOption() { pressOption(0) }

    fun pressBubbleOption() { pressOption(1) }

    fun pressQuickOption() { pressOption(2) }

    fun pressOptions() { rule.onNodeWithTag(DecisionDefaultDialogTags.OPTION_CHOSEN_TAG).performClick() }

    fun pressOption(position: Int) { rule.onNodeWithTag(DecisionDefaultDialogTags.OPTION_ROW_TAG + position, useUnmergedTree = true).performClick() }

    fun pressGo() { rule.onNodeWithTag(DecisionDefaultDialogTags.GO_BUTTON_TAG).performClick() }
}

fun decisionDefault(rule: ComposeContentTestRule, block: DecisionDefaultDialogRobot.() -> Unit) = DecisionDefaultDialogRobot(rule).apply(block)