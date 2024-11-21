package com.chrisburrow.helpdecide

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.HelpDecideApp
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.preferences.MockPreferencesLibrary
import com.chrisburrow.helpdecide.ui.robots.addDialog
import com.chrisburrow.helpdecide.ui.robots.decisionDefault
import com.chrisburrow.helpdecide.ui.robots.decisionDialog
import com.chrisburrow.helpdecide.ui.robots.decisionWheel
import com.chrisburrow.helpdecide.ui.robots.home
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PreferencesJourneyTest {

    @get:Rule
    val rule = createComposeRule()

    private lateinit var mockPreferencesLibrary: MockPreferencesLibrary

    fun setup(decisionType: String) {

        mockPreferencesLibrary = MockPreferencesLibrary(defaultDecisionOption = decisionType)

        rule.setContent {

            HelpDecideTheme {

                HelpDecideApp(
                    analyticsLibrary = MockAnalyticsLibrary(),
                    preferencesLibrary = mockPreferencesLibrary,
                    voiceCompatible = false
                )
            }
        }
    }

    @Test
    fun checkSpinTheWheelUsedIfNothingSet() = runTest {

        setup("")

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

                pressGo()
            }

            decisionWheel(rule) {


            }
        }
    }

    @Test
    fun checkSpinTheWheelUsed() = runTest {

        setup("spinTheWheel")

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

                pressGo()
            }

            decisionWheel(rule) {


            }
        }
    }

    @Test
    fun checkInstantUsed() = runTest {

        setup("instant")

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

                pressGo()
            }

            decisionDialog(rule) {


            }
        }
    }
}