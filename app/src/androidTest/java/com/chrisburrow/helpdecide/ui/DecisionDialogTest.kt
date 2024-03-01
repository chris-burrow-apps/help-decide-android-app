package com.chrisburrow.helpdecide.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.views.dialogs.DecisionDialog
import com.chrisburrow.helpdecide.ui.robots.decisionDialog
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.utils.OptionObject
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
                    options = listOf(expectedObject)
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
                    options = listOf(OptionObject(text = "example 1")),
                    clearPressed = {},
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
    fun clearPressed() {

        var clearCalled = false

        rule.setContent {

            HelpDecideTheme {

                DecisionDialog(
                    options = listOf(OptionObject(text = "example 1")),
                    clearPressed = { clearCalled = true },
                    donePressed = {}
                )
            }
        }

        decisionDialog(rule) {

            pressClear()
        }

        assertTrue(clearCalled)
    }
}