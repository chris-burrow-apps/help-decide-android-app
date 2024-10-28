package com.chrisburrow.helpdecide.ui

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.robots.onboarding
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.views.screens.OnboardingScreen
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnboardingScreenTest {

    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Test
    fun textShown() = runTest {

        val analyticsLibrary = MockAnalyticsLibrary()

        rule.setContent {

            HelpDecideTheme {

                OnboardingScreen(analyticsLibrary, { })
            }
        }

        onboarding(rule) {

            checkPageText(0, context.getString(R.string.onboarding_welcome_title), context.getString(R.string.onboarding_welcome_desc))
            checkSkipHidden(0)
            checkNextText(0, context.getString(R.string.next))
            pressNext(0)

            checkPageText(1, context.getString(R.string.crashalytics), context.getString(R.string.crashalytics_desc))
            checkSkipText(1, context.getString(R.string.disable))
            checkNextText(1, context.getString(R.string.enable))
            pressNext(1)

            checkPageText(2, context.getString(R.string.analytics), context.getString(R.string.analytics_desc))
            checkSkipText(2, context.getString(R.string.disable))
            checkNextText(2, context.getString(R.string.enable))
        }
    }

    @Test
    fun analyticsNextUpdated() = runTest {

    val analyticsLibrary = MockAnalyticsLibrary()
        var nextPageCalled = false

        rule.setContent {

            HelpDecideTheme {

                OnboardingScreen(analyticsLibrary) { nextPageCalled = true }
            }
        }

        onboarding(rule) {

            pressNext(0)
            checkPageShown(1)
            assertFalse(analyticsLibrary.setCrashalyticsStateCalled)
            assertFalse(analyticsLibrary.crashayticsState)
            assertFalse(analyticsLibrary.setAnalyticsStateCalled)
            assertFalse(analyticsLibrary.analyticsState)

            pressNext(1)
            checkPageShown(2)
            assertTrue(analyticsLibrary.setCrashalyticsStateCalled)
            assertTrue(analyticsLibrary.crashayticsState)

            pressNext(2)
            checkPageShown(2)
            assertTrue(analyticsLibrary.setAnalyticsStateCalled)
            assertTrue(analyticsLibrary.analyticsState)

            assertTrue(nextPageCalled)
        }
    }

    @Test
    fun analyticsSkipUpdated() = runTest {

        val analyticsLibrary = MockAnalyticsLibrary()
        var nextPageCalled = false

        rule.setContent {

            HelpDecideTheme {

                OnboardingScreen(analyticsLibrary) { nextPageCalled = true }
            }
        }

        onboarding(rule) {

            pressNext(0)
            checkPageShown(1)
            assertFalse(analyticsLibrary.setCrashalyticsStateCalled)
            assertFalse(analyticsLibrary.crashayticsState)
            assertFalse(analyticsLibrary.setAnalyticsStateCalled)
            assertFalse(analyticsLibrary.analyticsState)

            pressSkip(1)
            checkPageShown(2)
            assertTrue(analyticsLibrary.setCrashalyticsStateCalled)
            assertFalse(analyticsLibrary.crashayticsState)

            pressSkip(2)
            checkPageShown(2)
            assertTrue(analyticsLibrary.setAnalyticsStateCalled)
            assertFalse(analyticsLibrary.analyticsState)

            assertTrue(nextPageCalled)
        }
    }
}