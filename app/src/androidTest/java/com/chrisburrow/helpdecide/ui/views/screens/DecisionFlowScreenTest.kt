package com.chrisburrow.helpdecide.ui.views.screens

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.libraries.preferences.DecisionTypeLookup
import com.chrisburrow.helpdecide.ui.libraries.preferences.MockPreferencesLibrary
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DecisionFlowScreenTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun showDecisionDialog() = runTest {

        val preferencesLibrary = MockPreferencesLibrary(
            defaultDecisionOption = DecisionTypeLookup.INSTANT_KEY
        )

        var decisionTypeTriggered = false

        rule.setContent {

            HelpDecideTheme {

                DecisionFlowScreen(
                    preferencesLibrary = preferencesLibrary,
                    forceSkipDecisionTypeDialog = false,
                    showDecisionType = {
                        decisionTypeTriggered = true
                    },
                    showInstantDecision = {

                    },
                    showPopTheBubble = {

                    },
                    showSpinTheWheel = {

                    }
                )
            }
        }

        assertTrue(decisionTypeTriggered)
    }

    @Test
    fun showInstantDecision() = runTest {

        val preferencesLibrary = MockPreferencesLibrary(
            defaultDecisionOption = DecisionTypeLookup.INSTANT_KEY
        )

        var instantDecisionTriggered = false

        rule.setContent {

            HelpDecideTheme {

                DecisionFlowScreen(
                    preferencesLibrary = preferencesLibrary,
                    forceSkipDecisionTypeDialog = true,
                    showDecisionType = { },
                    showInstantDecision = { instantDecisionTriggered = true },
                    showPopTheBubble = { },
                    showSpinTheWheel = { }
                )
            }
        }

        assertTrue(instantDecisionTriggered)
    }

    @Test
    fun showPopTheBubble() = runTest {

        val preferencesLibrary = MockPreferencesLibrary(
            defaultDecisionOption = DecisionTypeLookup.PICK_A_BUBBLE_KEY
        )

        var popTheBubbleTriggered = false

        rule.setContent {

            HelpDecideTheme {

                DecisionFlowScreen(
                    preferencesLibrary = preferencesLibrary,
                    forceSkipDecisionTypeDialog = true,
                    showDecisionType = { },
                    showInstantDecision = { },
                    showPopTheBubble = { popTheBubbleTriggered = true },
                    showSpinTheWheel = { }
                )
            }
        }

        assertTrue(popTheBubbleTriggered)
    }

    @Test
    fun showSpinTheWheel() = runTest {

        val preferencesLibrary = MockPreferencesLibrary(
            defaultDecisionOption = DecisionTypeLookup.SPIN_THE_WHEEL_KEY
        )

        var spinTheWheelTriggered = false

        rule.setContent {

            HelpDecideTheme {

                DecisionFlowScreen(
                    preferencesLibrary = preferencesLibrary,
                    forceSkipDecisionTypeDialog = true,
                    showDecisionType = { },
                    showInstantDecision = { },
                    showPopTheBubble = { },
                    showSpinTheWheel = { spinTheWheelTriggered = true }
                )
            }
        }

        assertTrue(spinTheWheelTriggered)
    }
}