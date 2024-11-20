package com.chrisburrow.helpdecide

import android.content.Context
import android.os.SystemClock
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.chrisburrow.helpdecide.ui.HelpDecideApp
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.robots.addAnotherDialog
import com.chrisburrow.helpdecide.ui.robots.addDialog
import com.chrisburrow.helpdecide.ui.robots.clearAllDialog
import com.chrisburrow.helpdecide.ui.robots.decisionDefault
import com.chrisburrow.helpdecide.ui.robots.decisionDialog
import com.chrisburrow.helpdecide.ui.robots.decisionWheel
import com.chrisburrow.helpdecide.ui.robots.home
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeJourneyTest {

    @get:Rule
    val rule = createComposeRule()

    private val context: Context = InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun setup() {

        rule.setContent {

            HelpDecideTheme {

                HelpDecideApp(
                    analyticsLibrary = MockAnalyticsLibrary(settingsShown = true),
                    voiceCompatible = false
                )
            }
        }
    }

    @Test
    fun decideDisabled_whenNoOptionsAdded() = runTest {

        home(rule) {

            checkDecideDisabled()
        }
    }

    @Test
    fun optionCancelled() = runTest {

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
    fun optionsCanBeAdded() = runTest {

        home(rule) {

            pressAdd()

            addDialog(rule) {

                typeText("Option 1")
                pressSave()

                dialogHidden()
            }

            checkOptionShown(0, "Option 1")

            pressAdd()

            addDialog(rule) {

                typeText("Option 2")
                pressSave()

                dialogHidden()
            }

            checkOptionShown(1,"Option 2")

            checkDecideEnabled()
        }
    }

    @Test
    fun addAnotherShown() {

        home(rule) {

            pressAdd()

            addDialog(rule) {

                typeText(SystemClock.uptimeMillis().toString())
                pressSave()
            }

            checkDecideEnabled()

            pressDecide()

            addAnotherDialog(rule) {

                checkDescription(context.getString(R.string.add_another_desc))
                checkConfirm(context.getString(R.string.continue_option))
                checkCancel(context.getString(R.string.cancel))

                pressCancel()
            }

            pressDecide()

            addAnotherDialog(rule) {

                pressConfirm()
            }

            decisionDialog(rule) {

            }
        }
    }

    @Test
    fun deleteOneOption() = runTest {

        home(rule) {

            checkClearAllHidden()

            pressAdd()

            val optionOne = SystemClock.uptimeMillis().toString()

            addDialog(rule) {

                typeText(optionOne)
                pressSave()
            }

            pressAdd()

            val optionTwo = SystemClock.uptimeMillis().toString()

            addDialog(rule) {

                typeText(optionTwo)
                pressSave()
            }

            checkNumberOfOptions(2)

            pressDelete(0)

            checkNumberOfOptions(1)
            checkOptionShown(0, optionTwo)
        }
    }

    @Test
    fun clearAllOptions() = runTest {

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

            clearAllDialog(rule) {

                checkDescription(context.getString(R.string.confirm_delete_desc))
                checkConfirm(context.getString(R.string.delete_all_button))
                checkCancel(context.getString(R.string.cancel))

                pressConfirm()
            }

            checkNumberOfOptions(0)
            checkDecideDisabled()
            checkClearAllHidden()
        }
    }

    @Test
    fun decisionTextShown() = runTest {

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

            }
        }
    }
}