package com.chrisburrow.helpdecide

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.testing.TestNavHostController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.AppNavHost
import com.chrisburrow.helpdecide.ui.NavigationItem
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.robots.home
import com.chrisburrow.helpdecide.ui.robots.onboarding
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnboardingJourneyTest {

    @get:Rule
    val rule = createComposeRule()

    lateinit var analyticsLibrary: MockAnalyticsLibrary

    private lateinit var navController: TestNavHostController

    fun setup(settingsShown: Boolean) {

        analyticsLibrary = MockAnalyticsLibrary(settingsShown = settingsShown)

        rule.setContent {

            HelpDecideTheme {

                AppNavHost(
                    navController = navController,
                    analyticsLibrary = analyticsLibrary,
                    startDestination = NavigationItem.Loading.route
                )
            }
        }
    }

    @Test
    fun onboardingShownFirstRun() {

        setup(settingsShown = false)

        onboarding(rule) { }
    }

    @Test
    fun settingsHiddenIfPreviouslyShown() {

        setup(settingsShown = true)

        home(rule) {  }
    }
}