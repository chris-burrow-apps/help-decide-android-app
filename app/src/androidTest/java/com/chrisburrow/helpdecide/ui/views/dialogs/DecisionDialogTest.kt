package com.chrisburrow.helpdecide.ui.views.dialogs

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.robots.decisionDialog
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.DecisionViewModel
import com.chrisburrow.helpdecide.utils.OptionObject
import com.chrisburrow.helpdecide.utils.RandomGenerator
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DecisionDialogTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun textShown() {

        val expectedObject = OptionObject(text = "example 1")

        rule.setContent {

            HelpDecideTheme {

                DecisionDialog(
                    DecisionViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        randomGenerator = RandomGenerator(),
                        options = listOf(expectedObject)
                    )
                )
            }
        }

        decisionDialog(rule) {

            checkText(expectedObject.text)
        }
    }

    @Test
    fun donePressed() {

        var doneCalled = false

        rule.setContent {

            HelpDecideTheme {

                DecisionDialog(
                    DecisionViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        randomGenerator = RandomGenerator(),
                        options = listOf(OptionObject(text = "example 1"))
                    ),
                    removePressed = {},
                    donePressed = { doneCalled = true }
                )
            }
        }

        decisionDialog(rule) {

            pressDone()
        }

        assertTrue(doneCalled)
    }

    @Test
    fun removePressed() {

        var clearCalled = false
        var optionRemoved: OptionObject? = null

        var option = OptionObject(text = "example 1")

        rule.setContent {

            HelpDecideTheme {

                DecisionDialog(
                    DecisionViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        randomGenerator = RandomGenerator(),
                        options = listOf(option)
                    ),
                    removePressed = {
                        optionRemoved = it
                        clearCalled = true
                    },
                    donePressed = {}
                )
            }
        }

        decisionDialog(rule) {

            pressRemove()
        }

        assertTrue(clearCalled)
        assertEquals(option, optionRemoved)
    }

    @Test
    fun analyticsLogged() {

        val analyticsLibrary = MockAnalyticsLibrary()

        rule.setContent {

            HelpDecideTheme {

                DecisionDialog(
                    DecisionViewModel(
                        analyticsLibrary = analyticsLibrary,
                        randomGenerator = RandomGenerator(),
                        options = listOf(OptionObject(text = "example 1"))
                    )
                )
            }
        }

        decisionDialog(rule) {

            assertTrue(analyticsLibrary.logScreenCalledWith(AnalyticsScreens.Instant))

            pressDone()
            assertTrue(analyticsLibrary.logButtonCalledWith(AnalyticsActions.Done))

            pressRemove()
            assertTrue(analyticsLibrary.logButtonCalledWith(AnalyticsActions.RemoveOption))
        }
    }
}