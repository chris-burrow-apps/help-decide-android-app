package com.chrisburrow.helpdecide.ui.robots

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import com.chrisburrow.helpdecide.ui.views.screens.DecideWheelTags

class DecisionWheelDialogRobot(private val rule: ComposeContentTestRule) {

    init {
        rule.waitUntil {
            rule.onNodeWithTag(DecideWheelTags.BASE_VIEW_TAG).isDisplayed()
        }
    }
}

fun decisionWheel(rule: ComposeContentTestRule, block: DecisionWheelDialogRobot.() -> Unit) = DecisionWheelDialogRobot(rule).apply(block)