package com.chrisburrow.helpdecide.ui.views.dialogs

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.robots.generalDialog
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.GeneralDialogConfig
import com.chrisburrow.helpdecide.ui.viewmodels.GeneralDialogViewModel
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GeneralDialogTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun textShown() {

        val confirmText = "Confirm 1"
        val cancelText = "Cancel 1"
        val descText = "Desc 1"

        rule.setContent {

            HelpDecideTheme {

                GeneralDialog(
                    GeneralDialogViewModel(
                        configuration = GeneralDialogConfig(
                            screenName = "screenName",
                            description = descText,
                            confirmText = confirmText,
                            confirmPressed = { },
                            cancelText = cancelText,
                            cancelPressed = { },
                        ),
                        analyticsLibrary = MockAnalyticsLibrary())
                )
            }
        }

        generalDialog(rule) {

            checkDescription(descText)
            checkConfirm(confirmText)
            checkCancel(cancelText)
        }
    }

    @Test
    fun callbacksTriggered() {

        var confirmPressed = false
        var cancelPressed = false

        rule.setContent {

            HelpDecideTheme {

                GeneralDialog(
                    GeneralDialogViewModel(
                        configuration = GeneralDialogConfig(
                            screenName = "screenName",
                            description = "descText",
                            confirmText = "confirmText",
                            confirmPressed = { confirmPressed = true },
                            cancelText = "cancelText",
                            cancelPressed = { cancelPressed = true },
                        ),
                        analyticsLibrary = MockAnalyticsLibrary())
                )
            }
        }

        generalDialog(rule) {

            pressConfirm()
            assertTrue(confirmPressed)

            pressCancel()
            assertTrue(cancelPressed)
        }
    }

    @Test
    fun analyticsLogged() {

        val confirmText = "Confirm 1"
        val cancelText = "Cancel 1"
        val screenName = "Screen name 1"

        val analyticsLibrary = MockAnalyticsLibrary()

        rule.setContent {

            HelpDecideTheme {

                GeneralDialog(
                    GeneralDialogViewModel(
                        configuration = GeneralDialogConfig(
                            screenName = screenName,
                            description = "descText",
                            confirmText = confirmText,
                            confirmPressed = { },
                            cancelText = cancelText,
                            cancelPressed = { },
                        ),
                        analyticsLibrary = analyticsLibrary
                    )
                )
            }
        }

        generalDialog(rule) {

            assertTrue(analyticsLibrary.logScreenCalledWith(screenName))

            pressConfirm()
            assertTrue(analyticsLibrary.logButtonCalledWith(confirmText))

            pressCancel()
            assertTrue(analyticsLibrary.logButtonCalledWith(cancelText))
        }
    }
}