package com.chrisburrow.helpdecide.ui.robots

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.chrisburrow.helpdecide.ui.views.dialogs.DecideWheelDialogTags
import com.chrisburrow.helpdecide.ui.views.dialogs.DecisionDialogTags
import com.chrisburrow.helpdecide.ui.views.dialogs.SettingsDialogTags
import com.chrisburrow.helpdecide.ui.views.screens.settings.SettingsListTags

class SettingsRobot(private val rule: ComposeContentTestRule) {

    init {
        rule.onNodeWithTag(SettingsListTags.BASE_VIEW).assertIsDisplayed()
    }

    fun checkText(position: Int, title: String = "", desc: String = "") {

        rule.onNodeWithTag(SettingsListTags.TITLE_TAG + position).assertTextEquals(title)
        rule.onNodeWithTag(SettingsListTags.DESCRIPTION_TAG + position).assertTextEquals(desc)
    }

    fun checkToggleOn(position: Int) {

        rule.onNodeWithTag(SettingsListTags.SWITCH_TAG + position).assertIsOn()
    }

    fun checkToggleOff(position: Int) {

        rule.onNodeWithTag(SettingsListTags.SWITCH_TAG + position).assertIsOff()
    }

    fun pressToggle(position: Int) { rule.onNodeWithTag(SettingsListTags.SWITCH_TAG + position).performClick() }

    fun pressDone() { rule.onNodeWithTag(SettingsDialogTags.DONE_BUTTON_TAG).performClick() }
}

fun settings(rule: ComposeContentTestRule, block: SettingsRobot.() -> Unit) = SettingsRobot(rule).apply(block)