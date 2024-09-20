package com.chrisburrow.helpdecide

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.robots.home
import com.chrisburrow.helpdecide.ui.robots.settings
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.HomeViewModel
import com.chrisburrow.helpdecide.ui.views.screens.HomeScreen
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PermissionsJourneyTest {

    @get:Rule
    val rule = createComposeRule()

    lateinit var analyticsLibrary: MockAnalyticsLibrary

    fun setup(settingsShown: Boolean) {

        analyticsLibrary = MockAnalyticsLibrary(settingsShown = settingsShown)

        rule.setContent {

            HelpDecideTheme {

                HomeScreen(
                    analyticsLibrary,
                    HomeViewModel(
                        analyticsLibrary = analyticsLibrary,
                        isSpeechCompatible = false,
                        initialOptions = emptyList()
                    )
                )
            }
        }
    }

    @Test
    fun settingsShownFirstBoot() = runTest {

        setup(settingsShown = false)

        home(rule) {  }

        settings(rule) {

            assertTrue(analyticsLibrary.settingsShown)

            pressDone()
        }

        home(rule) { }
    }

    @Test
    fun settingsHiddenIfPreviouslyShown() = runTest {

        setup(settingsShown = true)

        home(rule) {  }
    }
}