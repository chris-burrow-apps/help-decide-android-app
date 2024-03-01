package com.chrisburrow.helpdecide.ui.robots

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.chrisburrow.helpdecide.ui.views.dialogs.DecideWheelDialogTags
import com.chrisburrow.helpdecide.ui.views.dialogs.DecisionDefaultDialogTags
import java.text.FieldPosition

class DecisionDefaultDialogRobot(private val rule: ComposeContentTestRule) {

    init {
        rule.onNodeWithTag(DecisionDefaultDialogTags.BASE_VIEW_TAG).assertIsDisplayed()
    }

    fun pressQuickOption() { pressOption(0) }

    fun pressWheelOption() { pressOption(1) }

    private fun pressOption(position: Int) { rule.onNodeWithTag(DecisionDefaultDialogTags.OPTION_ROW_TAG + position).performClick() }

    fun pressGo() { rule.onNodeWithTag(DecisionDefaultDialogTags.GO_BUTTON_TAG).performClick() }
}

fun decisionDefault(rule: ComposeContentTestRule, block: DecisionDefaultDialogRobot.() -> Unit) = DecisionDefaultDialogRobot(rule).apply(block)