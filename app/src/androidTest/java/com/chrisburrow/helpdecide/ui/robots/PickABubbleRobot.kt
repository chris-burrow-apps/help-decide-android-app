package com.chrisburrow.helpdecide.ui.robots

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.chrisburrow.helpdecide.ui.views.screens.PickABubbleTags

class PickABubbleRobot(private val rule: ComposeContentTestRule) {

    init {
        rule.waitUntil {
            rule.onNodeWithTag(PickABubbleTags.BASE_VIEW_TAG).isDisplayed()
        }
    }

    fun pressButton(position: Int) { rule.onNodeWithTag(PickABubbleTags.BUBBLE_BUTTON_CLICK_TAG + position).performClick() }

    fun checkNumberOfOptions(size: Int) { rule.onAllNodesWithTag(PickABubbleTags.BUBBLE_BUTTON_BASE_TAG).assertCountEquals(size) }
}


fun pickABubble(rule: ComposeContentTestRule, block: PickABubbleRobot.() -> Unit) = PickABubbleRobot(rule).apply(block)