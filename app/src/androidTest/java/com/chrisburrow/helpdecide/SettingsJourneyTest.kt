package com.chrisburrow.helpdecide

import android.os.SystemClock
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.views.screens.HomeScreen
import com.chrisburrow.helpdecide.ui.robots.addDialog
import com.chrisburrow.helpdecide.ui.robots.decisionDefault
import com.chrisburrow.helpdecide.ui.robots.decisionDialog
import com.chrisburrow.helpdecide.ui.robots.decisionWheel
import com.chrisburrow.helpdecide.ui.robots.home
import com.chrisburrow.helpdecide.ui.robots.settings
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsJourneyTest {

    @get:Rule
    val rule = createComposeRule()

    @Before
    fun setup() {

        rule.setContent {

            HelpDecideTheme {

                HomeScreen()
            }
        }
    }

    @Test
    fun showAllSettingsInOrder() {

        home(rule) {

            pressSettings()
        }

        settings(rule) {

            checkText(0, "Analytics", "Allowing me to see what parts of the app is used so I can improve it in future with extra features.")
            checkText(1, "Crashalytics", "If the app crashes, a crash report will be auto sent to me so I can diagnose what happened.")
        }
    }

    @Test
    fun doneClosesDialog() {

        home(rule) {

            pressSettings()
        }

        settings(rule) {

            pressDone()
        }

        home(rule) {

            // Closes menu and goes back to home
        }
    }
}