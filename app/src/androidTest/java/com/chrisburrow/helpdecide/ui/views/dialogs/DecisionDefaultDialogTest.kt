package com.chrisburrow.helpdecide.ui.views.dialogs

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.preferences.MockPreferencesLibrary
import com.chrisburrow.helpdecide.ui.robots.decisionDefault
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.DecisionDefaultViewModel
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

    val firstOptionKey = "optionOneKey"
    val firstOptionValue = "Option 1"

    val secondOptionKey = "optionTwoKey"
    val secondOptionValue = "Option 2"

    val options = linkedMapOf(firstOptionKey to firstOptionValue, secondOptionKey to secondOptionValue)

    @Test
    fun optionSelected() = runTest {

        var optionSelected = ""
        var selectedCallbackCalled = false

        rule.setContent {

            HelpDecideTheme {

                DecisionDefaultDialog(
                    viewModel = DecisionDefaultViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        preferencesLibrary = MockPreferencesLibrary(),
                        options = options
                    )
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
            assertEquals(secondOptionKey, optionSelected)
        }
    }

    @Test
    fun analyticsTracked() = runTest {

        val analyticsLibrary = MockAnalyticsLibrary()

        rule.setContent {

            HelpDecideTheme {

                DecisionDefaultDialog(
                    viewModel = DecisionDefaultViewModel(
                        analyticsLibrary = analyticsLibrary,
                        preferencesLibrary = MockPreferencesLibrary(),
                        options = options
                    )
                ) {

                }
            }
        }

        decisionDefault(rule) {

            assertTrue(analyticsLibrary.logScreenCalledWith(AnalyticsScreens.DECISION_TYPE))

            pressGo()

            assertTrue(analyticsLibrary.logButtonCalledWith(AnalyticsActions.GO))
        }
    }

    @Test
    fun optionSavedToPreferences() {

        val preferencesLibrary = MockPreferencesLibrary()

        rule.setContent {

            HelpDecideTheme {

                DecisionDefaultDialog(
                    viewModel = DecisionDefaultViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        preferencesLibrary = preferencesLibrary,
                        options = options
                    )
                ) {

                }
            }
        }

        decisionDefault(rule) {

            pressOptions()
            pressOption(1)

            pressGo()

            assertEquals(secondOptionKey, preferencesLibrary.defaultDecisionOption)
        }
    }

    @Test
    fun optionRetrievedFromPreferences() {

        val preferencesLibrary = MockPreferencesLibrary(defaultDecisionOption = secondOptionKey)

        rule.setContent {

            HelpDecideTheme {

                DecisionDefaultDialog(
                    viewModel = DecisionDefaultViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        preferencesLibrary = preferencesLibrary,
                        options = options
                    )
                ) {

                }
            }
        }

        decisionDefault(rule) {

            assertTrue(preferencesLibrary.checkDefaultDecisionOptionCalled)
            assertEquals(secondOptionKey, preferencesLibrary.defaultDecisionOption)
        }
    }

    @Test
    fun firstOptionRetrievedIfNoPreferenceSet() {

        var optionSelected = ""

        val preferencesLibrary = MockPreferencesLibrary(defaultDecisionOption = "")

        rule.setContent {

            HelpDecideTheme {

                DecisionDefaultDialog(
                    viewModel = DecisionDefaultViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        preferencesLibrary = preferencesLibrary,
                        options = options
                    )
                ) {
                    optionSelected = it
                }
            }
        }

        decisionDefault(rule) {

            pressGo()

            assertEquals(firstOptionKey, optionSelected)
        }
    }
}