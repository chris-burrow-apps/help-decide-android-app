package com.chrisburrow.helpdecide.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.views.dialogs.DecisionDialog
import com.chrisburrow.helpdecide.ui.robots.decisionDialog
import com.chrisburrow.helpdecide.ui.robots.decisionWheel
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.views.dialogs.DecideWheelDialog
import com.chrisburrow.helpdecide.utils.OptionObject
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DecisionWheelDialogTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun optionShown() {

        val expectedObject = OptionObject(text = "example 1")
        val options = listOf(expectedObject)

        rule.setContent {

            HelpDecideTheme {

                DecideWheelDialog(options = options, dismissPressed = { }, clearPressed = { })
            }
        }

        decisionWheel(rule) {

            checkText(expectedObject.text)
        }
    }

    @Test
    fun donePressed() {

        val expectedObject = OptionObject(text = "example 1")
        val options = listOf(expectedObject)

        var doneCalled = false

        rule.setContent {

            HelpDecideTheme {

                DecideWheelDialog(options = options, dismissPressed = { doneCalled = true }, clearPressed = { })
            }
        }

        decisionWheel(rule) {

            pressDone()
        }

        assertTrue(doneCalled)
    }

    @Test
    fun clearPressed() {

        val expectedObject = OptionObject(text = "example 1")
        val options = listOf(expectedObject)

        var clearCalled = false

        rule.setContent {

            HelpDecideTheme {

                DecideWheelDialog(options = options, dismissPressed = {  }, clearPressed = { clearCalled = true })
            }
        }

        decisionWheel(rule) {

            pressClear()
        }

        assertTrue(clearCalled)
    }
}