package com.chrisburrow.helpdecide.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.robots.settings
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.views.screens.settings.SettingsList
import com.chrisburrow.helpdecide.utils.SettingsBooleanRow
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsListTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun textShown() {

        val expectedList = listOf(

            SettingsBooleanRow(
                title = "Option 1",
                description = "Description 1",
                false
            ) { },
            SettingsBooleanRow(
                title = "Option 2",
                description = "Description 2",
                true
            ) { }
        )

        rule.setContent {

            HelpDecideTheme {

                SettingsList(options = expectedList)
            }
        }

        settings(rule) {

            expectedList.forEachIndexed { position, rowObject ->

                checkText(position, rowObject.title, rowObject.description)
            }
        }
    }

    @Test
    fun optionToggled() {

        var enabledCalled = false

        val expectedList = listOf(

            SettingsBooleanRow(
                title = "Option 1",
                description = "Description 1",
                false
            ) { enabledCalled = true },
        )

        rule.setContent {

            HelpDecideTheme {

                SettingsList(options = expectedList)
            }
        }

        settings(rule) {

            expectedList.forEachIndexed { position, rowObject ->

                checkToggle(position, false)

                pressToggle(position)

                checkToggle(position, true)

                assertTrue(enabledCalled)
            }
        }
    }
}