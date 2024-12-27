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
import com.google.firebase.installations.time.SystemClock
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

    private val expectedVersionName = SystemClock.getInstance().currentTimeMillis().toString()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    private val analyticsLibrary = MockAnalyticsLibrary(
        analyticsState = false,
        crashayticsState = true
    )

    private val preferencesLibrary = MockPreferencesLibrary(versionCode = expectedVersionName)

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

            assertTrue(analyticsLibrary.setAnalyticsStateCalled)
            assertTrue(analyticsLibrary.analyticsState)

            checkToggleOn(1)
            assertTrue(analyticsLibrary.getCrashalyticsStateCalled)

            pressToggle(1)

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
            checkText(2, context.getString(R.string.version_name), expectedVersionName)
        }
    }

    @Test
    fun packClosesScreen() = runTest {

        home(rule) {

            pressSettings()
        }

        settings(rule) {

            pressBack()
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

            assertTrue(analyticsLibrary.logScreenCalledWith(AnalyticsScreens.SETTINGS))
        }
    }
}