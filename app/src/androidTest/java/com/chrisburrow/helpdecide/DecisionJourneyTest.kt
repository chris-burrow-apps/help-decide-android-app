package com.chrisburrow.helpdecide

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.HelpDecideApp
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.preferences.DecisionTypeLookup
import com.chrisburrow.helpdecide.ui.libraries.preferences.MockPreferencesLibrary
import com.chrisburrow.helpdecide.ui.robots.addDialog
import com.chrisburrow.helpdecide.ui.robots.decisionChosenDialog
import com.chrisburrow.helpdecide.ui.robots.decisionDefault
import com.chrisburrow.helpdecide.ui.robots.decisionWheel
import com.chrisburrow.helpdecide.ui.robots.home
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DecisionJourneyTest {

    @get:Rule
    val rule = createComposeRule()

    private val analyticsLibrary = MockAnalyticsLibrary()

    fun setup(doNotAskToggle : Boolean) {

        rule.setContent {

            HelpDecideTheme {

                HelpDecideApp(
                    analyticsLibrary = analyticsLibrary,
                    preferencesLibrary = MockPreferencesLibrary(
                        defaultDecisionOption = DecisionTypeLookup.SPIN_THE_WHEEL_KEY,
                        shouldSkipDecisionType = doNotAskToggle
                    ),
                    voiceCompatible = false
                )
            }
        }
    }

    @Test
    fun decisionTypeDialogShown() = runTest {

        setup(doNotAskToggle = false)

        home(rule) {

            pressAdd()

            addDialog(rule) {

                typeText("Option 1")
                pressAdd()

                dialogHidden()
            }

            pressAdd()

            addDialog(rule) {

                typeText("Option 2")
                pressAdd()

                dialogHidden()
            }

            pressDecide()

            decisionDefault(rule) {


            }
        }
    }

    @Test
    fun decisionTypeDialogNotShown() = runTest {

        setup(doNotAskToggle = true)

        home(rule) {

            pressAdd()

            addDialog(rule) {

                typeText("Option 1")
                pressAdd()

                dialogHidden()
            }

            pressAdd()

            addDialog(rule) {

                typeText("Option 2")
                pressAdd()

                dialogHidden()
            }

            pressDecide()
        }

        decisionWheel(rule) {

        }
    }
}