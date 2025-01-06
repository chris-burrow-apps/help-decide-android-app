package com.chrisburrow.helpdecide.ui.views.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.robots.decisionWheel
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.DecideWheelViewModel
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
    fun optionChosen() = runTest {

        val optionText = "example 1"

        val expectedObjectOne = OptionObject(text = optionText)
        val expectedObjectTwo = OptionObject(text = optionText)
        val options = listOf(expectedObjectOne, expectedObjectTwo)

        rule.setContent {

            HelpDecideTheme {

                DecideWheelScreen(
                    DecideWheelViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        options = options
                    ),
                    backPressed = { },
                    donePressed = { },
                    removePressed = { }
                )
            }
        }

        decisionWheel(rule) {

            checkText(optionText)
        }
    }

    @Test
    fun removeChosen() = runTest {

        val expectedObjectOne = OptionObject(text = "example 1")
        val options = listOf(expectedObjectOne)

        var removeCalled = false
        var optionIdChosen: String? = null

        rule.setContent {

            HelpDecideTheme {

                DecideWheelScreen(
                    DecideWheelViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        options = options
                    ),
                    backPressed = { },
                    donePressed = { },
                    removePressed = {
                        removeCalled = true
                        optionIdChosen = it
                    }
                )
            }
        }

        decisionWheel(rule) {

            pressRemove()

            assertTrue(removeCalled)
            assertEquals(expectedObjectOne.id, optionIdChosen)
        }
    }

    @Test
    fun doneChosen() = runTest {

        val expectedObjectOne = OptionObject(text = "example 1")
        val options = listOf(expectedObjectOne)

        var doneCalled = false

        rule.setContent {

            HelpDecideTheme {

                DecideWheelScreen(
                    DecideWheelViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        options = options
                    ),
                    backPressed = { },
                    donePressed = { doneCalled = true },
                    removePressed = { }
                )
            }
        }

        decisionWheel(rule) {

            pressDone()

            assertTrue(doneCalled)
        }
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
                    donePressed = { },
                    removePressed = { }
                )
            }
        }

        decisionWheel(rule) {

            assertTrue(analyticsLibrary.logScreenCalledWith(AnalyticsScreens.WHEEL))

            pressDone()
            assertTrue(analyticsLibrary.logButtonCalledWith(AnalyticsActions.DONE))

            pressRemove()
            assertTrue(analyticsLibrary.logButtonCalledWith(AnalyticsActions.REMOVE_OPTION))
        }
    }
}