package com.chrisburrow.helpdecide.ui.views.dialogs

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.robots.decisionDefault
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DecisionDefaultDialogTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun optionSelected() = runTest {

        var optionSelected: Int = 0
        var selectedCallbackCalled = false

        rule.setContent {

            HelpDecideTheme {

                DecisionDefaultDialog(
                    analyticsLibrary = MockAnalyticsLibrary(),
                    options = listOf("Option 1", "Option 2"),
                    previouslySelected = 0,
                ) {
                    selectedCallbackCalled = true
                    optionSelected = it
                }
            }
        }

        decisionDefault(rule) {

            pressOptions()
            pressOption(1)

            pressGo()

            assertTrue(selectedCallbackCalled)
            assertEquals(1, optionSelected)
        }
    }

    @Test
    fun analyticsTracked() = runTest {

        val  analyticsLibrary = MockAnalyticsLibrary()

        rule.setContent {

            HelpDecideTheme {

                DecisionDefaultDialog(
                    analyticsLibrary = analyticsLibrary,
                    options = listOf("Option 1", "Option 2"),
                    previouslySelected = 0,
                ) {

                }
            }
        }

        decisionDefault(rule) {

            assertTrue(analyticsLibrary.logScreenCalledWith(AnalyticsScreens.DecisionType))

            pressGo()

            assertTrue(analyticsLibrary.logButtonCalledWith(AnalyticsActions.Go))
        }
    }
}