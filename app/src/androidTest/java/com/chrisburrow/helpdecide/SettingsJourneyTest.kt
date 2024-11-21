package com.chrisburrow.helpdecide

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.chrisburrow.helpdecide.ui.HelpDecideApp
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.preferences.MockPreferencesLibrary
import com.chrisburrow.helpdecide.ui.robots.home
import com.chrisburrow.helpdecide.ui.robots.settings
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsJourneyTest {

    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    val analyticsLibrary = MockAnalyticsLibrary(
        analyticsState = false,
        crashayticsState = true
    )

    val preferencesLibrary = MockPreferencesLibrary()

    @Before
    fun setup() {

        rule.setContent {

            HelpDecideTheme {

                HelpDecideApp(
                    analyticsLibrary = analyticsLibrary,
                    preferencesLibrary = preferencesLibrary,
                    voiceCompatible = false
                )
            }
        }
    }

    @Test
    fun checkSettingsScreen() = runTest {

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
    fun checkSettingsShown() = runTest {

        home(rule) {

            pressSettings()
        }

        settings(rule) {

            checkText(0, context.getString(R.string.analytics), context.getString(R.string.analytics_desc))
            checkText(1, context.getString(R.string.crashalytics), context.getString(R.string.crashalytics_desc))
        }
    }

    @Test
    fun doneClosesDialog() = runTest {

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
    fun analyticsLogged() = runTest {

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