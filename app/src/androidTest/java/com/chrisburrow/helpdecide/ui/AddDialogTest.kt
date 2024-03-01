package com.chrisburrow.helpdecide.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.views.dialogs.AddOptionDialog
import com.chrisburrow.helpdecide.ui.robots.addDialog
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddDialogTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun textShown() {

        rule.setContent {

            HelpDecideTheme {

                AddOptionDialog()
            }
        }

        val testTextInput = "Option 1"

        addDialog(rule) {

            isTextFocused()
            isClearDisabled()
            isSaveDisabled()

            typeText(testTextInput)

            isSaveEnabled()
            isClearEnabled()

            pressClear()

            isSaveDisabled()
            isClearDisabled()
        }
    }

    @Test
    fun optionAdded() {

        var returnedString = ""

        rule.setContent {

            HelpDecideTheme {

                AddOptionDialog(optionSaved = { returnedString = it })
            }
        }

        val testTextInput = "Option 1"

        addDialog(rule) {

            typeText(testTextInput)

            pressSave()
        }

        assertEquals(testTextInput, returnedString)
    }

    @Test
    fun optionCancelled() {

        var cancelledCalled = false

        rule.setContent {

            HelpDecideTheme {

                AddOptionDialog(optionCancelled = { cancelledCalled = true })
            }
        }

        val testTextInput = "Option 1"

        addDialog(rule) {

            typeText(testTextInput)

            pressCancel()
        }

        assertTrue(cancelledCalled)
    }

    @Test
    fun optionTextPaddingRemoved() {

        var returnedString = ""

        rule.setContent {

            HelpDecideTheme {

                AddOptionDialog(optionSaved = { returnedString = it })
            }
        }

        val testTextInput = "Option 1         "
        val expectedTextInput = "Option 1"

        addDialog(rule) {

            typeText(testTextInput)

            pressSave()
        }

        assertEquals(expectedTextInput, returnedString)
    }
}