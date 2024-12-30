package com.chrisburrow.helpdecide.ui.views.dialogs

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.robots.decisionWheel
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.DecideWheelViewModel
import com.chrisburrow.helpdecide.ui.views.screens.DecideWheelScreen
import com.chrisburrow.helpdecide.utils.OptionObject
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DecisionWheelScreenTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun optionShown() = runTest {

        val expectedObjectOne = OptionObject(text = "example 1")
        val expectedObjectTwo = OptionObject(text = "example 2")
        val options = listOf(expectedObjectOne, expectedObjectTwo)

        rule.setContent {

            HelpDecideTheme {

                DecideWheelScreen(
                    DecideWheelViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        options = options
                    ),
                    backPressed = { },
                    optionChosen = { }
                )
            }
        }

        decisionWheel(rule) {

        }
    }

    @Test
    fun optionChosen() = runTest {

        val expectedObjectOne = OptionObject(text = "example 1")
        val expectedObjectTwo = OptionObject(text = "example 2")
        val options = listOf(expectedObjectOne, expectedObjectTwo)

        var optionChosenCalled = false
        var optionIdChosen: String? = null

        rule.setContent {

            HelpDecideTheme {

                DecideWheelScreen(
                    DecideWheelViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        options = options
                    ),
                    optionChosen = {
                        optionChosenCalled = true
                        optionIdChosen = it
                    },
                    backPressed = { }
                )
            }
        }

        decisionWheel(rule) {

            Thread.sleep(500)
        }

        assertTrue(optionChosenCalled)
    }

    @Test
    fun analyticsCalled() = runTest {

        val expectedObjectOne = OptionObject(text = "example 1")
        val expectedObjectTwo = OptionObject(text = "example 2")
        val options = listOf(expectedObjectOne, expectedObjectTwo)

        val analyticsLibrary = MockAnalyticsLibrary()

        rule.setContent {

            HelpDecideTheme {

                DecideWheelScreen(
                    DecideWheelViewModel(
                        analyticsLibrary = analyticsLibrary,
                        options = options
                    ),
                    backPressed = { },
                    optionChosen = { }
                )
            }
        }

        decisionWheel(rule) {

            assertTrue(analyticsLibrary.logScreenCalledWith(AnalyticsScreens.WHEEL))
        }
    }
}