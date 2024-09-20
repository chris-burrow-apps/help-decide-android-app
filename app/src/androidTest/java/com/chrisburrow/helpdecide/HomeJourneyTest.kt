package com.chrisburrow.helpdecide

import android.os.SystemClock
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.robots.addDialog
import com.chrisburrow.helpdecide.ui.robots.decisionDefault
import com.chrisburrow.helpdecide.ui.robots.decisionDialog
import com.chrisburrow.helpdecide.ui.robots.decisionWheel
import com.chrisburrow.helpdecide.ui.robots.home
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.HomeViewModel
import com.chrisburrow.helpdecide.ui.views.screens.HomeScreen
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeJourneyTest {

    @get:Rule
    val rule = createComposeRule()

    @Before
    fun setup() {

        rule.setContent {

            HelpDecideTheme {

                HomeScreen(
                    MockAnalyticsLibrary(),
                    HomeViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        isSpeechCompatible = false,
                        initialOptions = emptyList()
                    )
                )
            }
        }
    }

    @Test
    fun decideDisabled_whenNoOptionsAdded() {

        home(rule) {

            checkDecideDisabled()
        }
    }

    @Test
    fun optionCancelled() {

        home(rule) {

            pressAdd()

            addDialog(rule) {

                typeText("Option 1")
                pressCancel()

                dialogHidden()
            }


            checkDecideDisabled()

            pressAdd()

            addDialog(rule) {

                checkText("")

                isSaveDisabled()
                isClearDisabled()
            }
        }
    }

    @Test
    fun optionsShown() {

        home(rule) {

            pressAdd()

            addDialog(rule) {

                typeText("Option 1")
                pressSave()

                dialogHidden()
            }

            optionShown(0, "Option 1")

            pressAdd()

            addDialog(rule) {

                typeText("Option 2")
                pressSave()

                dialogHidden()
            }

            optionShown(1,"Option 2")

            pressDecide()
        }
    }

    @Test
    fun deleteOptions() {

        home(rule) {

            checkClearAllHidden()

            pressAdd()

            addDialog(rule) {

                typeText(SystemClock.uptimeMillis().toString())
                pressSave()
            }

            checkClearAllShown()

            pressAdd()

            addDialog(rule) {

                typeText(SystemClock.uptimeMillis().toString())
                pressSave()
            }

            checkClearAllShown()

            pressClearAll()

            checkNumberOfOptions(0)
            checkDecideDisabled()
            checkClearAllHidden()
        }
    }

    @Test
    fun decisionTextShown() {

        val optionText = "Option 1"

        home(rule) {

            pressAdd()

            addDialog(rule) {

                typeText(optionText)
                pressSave()
            }

            pressAdd()

            addDialog(rule) {

                typeText(optionText)
                pressSave()
            }

            pressDecide()

            decisionDefault(rule) {

                pressOptions()
                pressQuickOption()
                pressGo()
            }

            decisionDialog(rule) {

                checkText(optionText)
                pressDone()
            }

            pressDecide()

            decisionDefault(rule) {

                pressOptions()
                pressQuickOption()
                pressGo()
            }

            decisionDialog(rule) {

                checkText(optionText)
                pressClear()
            }

            checkDecideDisabled()
        }
    }

    @Test
    fun decisionWheelShown() {

        val optionText = "Option 1"

        home(rule) {

            pressAdd()

            addDialog(rule) {

                typeText(optionText)
                pressSave()
            }

            pressAdd()

            addDialog(rule) {

                typeText(optionText)
                pressSave()
            }

            pressDecide()

            decisionDefault(rule) {

                pressOptions()
                pressWheelOption()
                pressGo()
            }

            decisionWheel(rule) {

                checkText(optionText)
                pressDone()
            }

            pressDecide()

            decisionDefault(rule) {

                pressOptions()
                pressWheelOption()
                pressGo()
            }

            decisionWheel(rule) {

                checkText(optionText)
                pressClear()
            }

            checkDecideDisabled()
        }
    }
}