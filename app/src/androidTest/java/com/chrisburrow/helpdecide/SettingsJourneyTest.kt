package com.chrisburrow.helpdecide

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.libraries.StorageLibraryKeys
import com.chrisburrow.helpdecide.ui.mock.MockStorage
import com.chrisburrow.helpdecide.ui.robots.home
import com.chrisburrow.helpdecide.ui.robots.settings
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.views.screens.HomeScreen
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsJourneyTest {

    @get:Rule
    val rule = createComposeRule()

    private var mockStorage = MockStorage(booleanValues = mutableMapOf(
        StorageLibraryKeys.AnalyticsEnabled to false,
        StorageLibraryKeys.CrashalyicsEnabled to true,
    ))

    @Before
    fun setup() {

        rule.setContent {

            HelpDecideTheme {

                HomeScreen(mockStorage)
            }
        }
    }

    @Test
    fun checkSettingsScreen() {

        home(rule) {

            pressSettings()
        }

        settings(rule) {

            var keyCalled = StorageLibraryKeys.AnalyticsEnabled

            checkToggleOff(0)
            assertTrue(mockStorage.didGetBooleanCalled(keyCalled))

            pressToggle(0)
            checkToggleOn(0)
            assertTrue(mockStorage.didStoreBooleanCalledWithValue(keyCalled, true))

            keyCalled = StorageLibraryKeys.CrashalyicsEnabled

            checkToggleOn(1)
            assertTrue(mockStorage.didGetBooleanCalled(keyCalled))

            pressToggle(1)
            checkToggleOff(1)
            assertTrue(mockStorage.didStoreBooleanCalledWithValue(keyCalled, false))
        }
    }

    @Test
    fun checkSettingsShown() {

        home(rule) {

            pressSettings()
        }

        settings(rule) {

            checkText(0, "Analytics", "Permission to allow the developer to see what is used in the app. This makes it easier in future to know what extra features can be made depending upon features currently used.")
            checkText(1, "Crashalytics", "If the app crashes, a crash report will be auto sent so the developer can diagnose what happened.")
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