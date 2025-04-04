package com.chrisburrow.helpdecide

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.HelpDecideApp
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.preferences.MockPreferencesLibrary
import com.chrisburrow.helpdecide.ui.robots.home
import com.chrisburrow.helpdecide.ui.robots.onboarding
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnboardingJourneyTest {

    @get:Rule
    val rule = createComposeRule()

    private val analyticsLibrary = MockAnalyticsLibrary()
    private val preferencesLibrary = MockPreferencesLibrary(onboardingShown = false)

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
    fun checkAnalyticsEnabled() = runTest {

        onboarding(rule) {

            pressNext(0)

            assertFalse(analyticsLibrary.crashayticsState)

            pressNext(1)

            assertTrue(analyticsLibrary.crashayticsState)
            assertFalse(analyticsLibrary.analyticsState)

            pressNext(2)

            assertTrue(analyticsLibrary.analyticsState)
        }

        home(rule) {

            checkEmptyShown()
            checkClearAllHidden()
        }
    }

    @Test
    fun checkAnalyticsDisabled() = runTest {

        onboarding(rule) {

            pressNext(0)

            assertFalse(analyticsLibrary.crashayticsState)

            pressSkip(1)

            Thread.sleep(500)
            assertFalse(analyticsLibrary.crashayticsState)
            assertTrue(analyticsLibrary.setCrashalyticsStateCalled)

            assertFalse(analyticsLibrary.analyticsState)

            pressSkip(2)

            Thread.sleep(500)
            assertFalse(analyticsLibrary.analyticsState)
            assertTrue(analyticsLibrary.setAnalyticsStateCalled)
        }

        home(rule) {

            checkEmptyShown()
            checkClearAllHidden()
        }
    }
}