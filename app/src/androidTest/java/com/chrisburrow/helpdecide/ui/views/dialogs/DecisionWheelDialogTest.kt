package com.chrisburrow.helpdecide.ui.views.dialogs

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
class DecisionWheelDialogTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun optionShown() = runTest {

        val expectedObject = OptionObject(text = "example 1")
        val options = listOf(expectedObject)

        rule.setContent {

            HelpDecideTheme {

                DecideWheelDialog(
                    DecideWheelViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        options = options
                    ),
                    dismissPressed = { },
                    removePressed = { }
                )
            }
        }

        decisionWheel(rule) {

            checkText(expectedObject.text)
        }
    }

    @Test
    fun donePressed() = runTest {

        val expectedObject = OptionObject(text = "example 1")
        val options = listOf(expectedObject)

        var doneCalled = false

        rule.setContent {

            HelpDecideTheme {

                DecideWheelDialog(
                    DecideWheelViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        options = options
                    ),
                    dismissPressed = { doneCalled = true },
                    removePressed = { }
                )
            }
        }

        decisionWheel(rule) {

            pressDone()
        }

        assertTrue(doneCalled)
    }

    @Test
    fun clearPressed() = runTest {

        val expectedObject = OptionObject(text = "example 1")
        val options = listOf(expectedObject)

        var clearCalled = false

        var optionRemoved: String? = null

        rule.setContent {

            HelpDecideTheme {

                DecideWheelDialog(
                    DecideWheelViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        options = options
                    ),
                    dismissPressed = {  },
                    removePressed = {
                        optionRemoved = it
                        clearCalled = true
                    }
                )
            }
        }

        decisionWheel(rule) {

            pressRemove()
        }

        assertTrue(clearCalled)
        assertEquals(expectedObject.id, optionRemoved)
    }

    @Test
    fun analyticsCalled() = runTest {

        val options = listOf(OptionObject(text = "example 1"))

        val analyticsLibrary = MockAnalyticsLibrary()

        rule.setContent {

            HelpDecideTheme {

                DecideWheelDialog(
                    DecideWheelViewModel(
                        analyticsLibrary = analyticsLibrary,
                        options = options
                    ),
                    dismissPressed = { },
                    removePressed = { }
                )
            }
        }

        decisionWheel(rule) {

            assertTrue(analyticsLibrary.logScreenCalledWith(AnalyticsScreens.Wheel))

            pressDone()
            assertTrue(analyticsLibrary.logButtonCalledWith(AnalyticsActions.Done))

            pressRemove()
            assertTrue(analyticsLibrary.logButtonCalledWith(AnalyticsActions.RemoveOption))
        }
    }
}