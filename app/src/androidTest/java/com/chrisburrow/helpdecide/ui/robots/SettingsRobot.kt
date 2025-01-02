package com.chrisburrow.helpdecide.ui.robots

import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import com.chrisburrow.helpdecide.ui.views.screens.SettingsScreenTags
import com.chrisburrow.helpdecide.ui.views.screens.settings.SettingsListTags

class SettingsRobot(private val rule: ComposeContentTestRule) {

    init {
        rule.waitUntil {
            rule.onNodeWithTag(SettingsListTags.BASE_VIEW).isDisplayed()
        }
    }

    fun checkText(position: Int, title: String = "", desc: String = "") {

        rule.onNodeWithTag(SettingsListTags.BASE_VIEW).performScrollToIndex(position)
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

    fun pressOption(position: Int) { rule.onNodeWithTag(SettingsListTags.ROW_VIEW + position).performClick() }

    fun pressBack() { rule.onNodeWithTag(SettingsScreenTags.BACK_BUTTON_TAG).performClick() }
}

fun settings(rule: ComposeContentTestRule, block: SettingsRobot.() -> Unit) = SettingsRobot(rule).apply(block)