package com.chrisburrow.helpdecide

import android.content.Context
import android.os.SystemClock
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.chrisburrow.helpdecide.ui.HelpDecideApp
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.preferences.MockPreferencesLibrary
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

    private val analyticsLibrary = MockAnalyticsLibrary()

    @Before
    fun setup() {

        rule.setContent {

            HelpDecideTheme {

                HelpDecideApp(
                    analyticsLibrary = analyticsLibrary,
                    preferencesLibrary = MockPreferencesLibrary(),
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

                isAddDisabled()
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
                pressAdd()

                dialogHidden()
            }

            checkOptionShown(0, "Option 1")

            pressAdd()

            addDialog(rule) {

                typeText("Option 2")
                pressAdd()

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
                pressAdd()
            }

            checkDecideEnabled()

            pressDecide()

            addAnotherDialog(rule) {

                analyticsLibrary.logScreenCalledWith(AnalyticsScreens.AddAnother)

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
                pressAdd()
            }

            pressAdd()

            val optionTwo = SystemClock.uptimeMillis().toString()

            addDialog(rule) {

                typeText(optionTwo)
                pressAdd()
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
                pressAdd()
            }

            checkClearAllShown()

            pressAdd()

            addDialog(rule) {

                typeText(SystemClock.uptimeMillis().toString())
                pressAdd()
            }

            checkClearAllShown()
            pressClearAll()

            clearAllDialog(rule) {

                analyticsLibrary.logScreenCalledWith(AnalyticsScreens.RemoveAll)

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
                pressQuickOption()
                pressGo()
            }

            decisionDialog(rule) {

            }
        }
    }
}