package com.chrisburrow.helpdecide

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.HelpDecideApp
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.preferences.MockPreferencesLibrary
import com.chrisburrow.helpdecide.ui.robots.addDialog
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
class SpinTheWheelJourneyTest {

    @get:Rule
    val rule = createComposeRule()

    @Before
    fun setup() {

        rule.setContent {

            HelpDecideTheme {

                HelpDecideApp(
                    analyticsLibrary = MockAnalyticsLibrary(),
                    preferencesLibrary = MockPreferencesLibrary(),
                    voiceCompatible = false
                )
            }
        }
    }

    @Test
    fun decisionWheelShown() = runTest {

        val optionText = "Option 1"

        home(rule) {

            pressAdd()

            addDialog(rule) {

                typeText(optionText)
                pressAdd()
            }

            pressAdd()

            addDialog(rule) {

                typeText(optionText)
                pressAdd()
            }

            pressDecide()

            decisionDefault(rule) {

                pressOptions()
                pressWheelOption()
                pressGo()
            }
        }

        decisionWheel(rule) {

            Thread.sleep(1000)
            pressDone()
        }

        home(rule) {

        }
    }
}