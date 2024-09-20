package com.chrisburrow.helpdecide

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.storage.StorageLibraryKeys
import com.chrisburrow.helpdecide.ui.libraries.storage.MockStorage
import com.chrisburrow.helpdecide.ui.robots.home
import com.chrisburrow.helpdecide.ui.robots.settings
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.HomeViewModel
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

    val analyticsLibrary = MockAnalyticsLibrary(
        analyticsState = false,
        crashayticsState = true
    )

    @Before
    fun setup() {

        rule.setContent {

            HelpDecideTheme {

                HomeScreen(
                    analyticsLibrary = analyticsLibrary,
                    model = HomeViewModel(
                        analyticsLibrary = analyticsLibrary,
                        isSpeechCompatible = false,
                        initialOptions = emptyList()
                    )
                )
            }
        }
    }

    @Test
    fun checkSettingsScreen() {

        home(rule) {

            pressSettings()
        }

        settings(rule) {

            checkToggleOff(0)
            assertTrue(analyticsLibrary.getAnalyticsStateCalled)

            pressToggle(0)
            checkToggleOn(0)
            assertTrue(analyticsLibrary.setAnalyticsStateCalled)
            assertTrue(analyticsLibrary.analyticsState)

            checkToggleOn(1)
            assertTrue(analyticsLibrary.getCrashalyticsStateCalled)

            pressToggle(1)
            checkToggleOff(1)
            assertTrue(analyticsLibrary.setCrashalyticsStateCalled)
            assertFalse(analyticsLibrary.crashayticsState)
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

    @Test
    fun analyticsLogged() {

        home(rule) {

            pressSettings()
        }

        settings(rule) {

            assertTrue(analyticsLibrary.logScreenCalledWith(AnalyticsScreens.Settings))

            pressDone()
            assertTrue(analyticsLibrary.logButtonCalledWith(AnalyticsActions.Done))
        }
    }
}