package com.chrisburrow.helpdecide.ui.views.dialogs

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.robots.addDialog
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.AddOptionViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddDialogTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun textShown() = runTest {

        rule.setContent {

            HelpDecideTheme {

                AddOptionDialog(viewModel = AddOptionViewModel(MockAnalyticsLibrary()))
            }
        }

        val testTextInput = "Option 1"

        addDialog(rule) {

            isTextFocused()
            isClearDisabled()
            isAddDisabled()

            typeText(testTextInput)

            isAddEnabled()
            isClearEnabled()

            pressClear()

            isAddDisabled()
            isClearDisabled()
        }
    }

    @Test
    fun optionAdded() = runTest {

        var returnedString = ""

        rule.setContent {

            HelpDecideTheme {

                AddOptionDialog(
                    viewModel = AddOptionViewModel(MockAnalyticsLibrary()),
                    optionSaved = { returnedString = it }
                )
            }
        }

        val testTextInput = "Option 1"

        addDialog(rule) {

            typeText(testTextInput)

            pressAdd()
        }

        assertEquals(testTextInput, returnedString)
    }

    @Test
    fun optionCancelled() = runTest {

        var cancelledCalled = false

        rule.setContent {

            HelpDecideTheme {

                AddOptionDialog(
                    viewModel = AddOptionViewModel(MockAnalyticsLibrary()),
                    optionCancelled = { cancelledCalled = true }
                )
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
    fun optionTextPaddingRemovedUponSubmit() = runTest {

        var returnedString = ""

        rule.setContent {

            HelpDecideTheme {

                AddOptionDialog(
                    viewModel = AddOptionViewModel(MockAnalyticsLibrary()),
                    optionSaved = { returnedString = it }
                )
            }
        }

        val testTextInput = "Option 1         "
        val expectedTextInput = "Option 1"

        addDialog(rule) {

            typeText(testTextInput)
            checkText(testTextInput)

            pressAdd()
        }

        assertEquals(expectedTextInput, returnedString)
    }

    @Test
    fun analyticsLogged() = runTest {

        val analyticsLibrary = MockAnalyticsLibrary()

        rule.setContent {

            HelpDecideTheme {

                AddOptionDialog(
                    viewModel = AddOptionViewModel(analyticsLibrary),
                    optionSaved = { }
                )
            }
        }

        addDialog(rule) {

            assertTrue(analyticsLibrary.logScreenCalledWith(AnalyticsScreens.ADD_TEXT))

            typeText("Option 1")

            pressAdd()
            assertTrue(analyticsLibrary.logButtonCalledWith(AnalyticsActions.ADD))

            pressClear()
            assertTrue(analyticsLibrary.logButtonCalledWith(AnalyticsActions.CLEAR))

            pressCancel()
            assertTrue(analyticsLibrary.logButtonCalledWith(AnalyticsActions.CANCEL))
        }
    }
}