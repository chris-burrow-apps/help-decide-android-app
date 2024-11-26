package com.chrisburrow.helpdecide.ui.views.dialogs

import android.os.SystemClock
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.robots.pickABubble
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.PickABubbleViewModel
import com.chrisburrow.helpdecide.ui.views.screens.PickABubbleScreen
import com.chrisburrow.helpdecide.utils.OptionObject
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PickABubbleScreenTest {

    @get:Rule
    val rule = createComposeRule()

    @Test
    fun optionShown() = runTest {

        val options = listOf(
            OptionObject(text = "example 1"),
            OptionObject(text = "example 2")
        )

        rule.setContent {

            HelpDecideTheme {

                PickABubbleScreen(
                    model = PickABubbleViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        options = options
                    ),
                    optionPressed = {},
                    backPressed = {}
                )
            }
        }

        pickABubble(rule) {

            checkNumberOfOptions(1)
        }
    }

    @Test
    fun optionPressed() = runTest {

        val expectedOptionId = SystemClock.uptimeMillis().toString()

        val options = listOf(
            OptionObject(id = expectedOptionId, text = "example 1"),
            OptionObject(text = "example 2")
        )

        var optionPressed = false
        var optionIdSelected = ""

        rule.setContent {

            HelpDecideTheme {

                PickABubbleScreen(
                    model = PickABubbleViewModel(
                        analyticsLibrary = MockAnalyticsLibrary(),
                        options = options,
                    ),
                    optionPressed = {
                        optionPressed = true
                        optionIdSelected = it
                    },
                    backPressed = {}
                )
            }
        }

        pickABubble(rule) {

            pressButton(0)
        }

        assertTrue(optionPressed)
        assertEquals(expectedOptionId, optionIdSelected)
    }

    @Test
    fun analyticsCalled() = runTest {

        val options = listOf(OptionObject(text = "example 1"))

        val analyticsLibrary = MockAnalyticsLibrary()

        rule.setContent {

            HelpDecideTheme {

                PickABubbleScreen(
                    PickABubbleViewModel(
                        analyticsLibrary = analyticsLibrary,
                        options = options
                    ),
                    optionPressed = { },
                    backPressed = { }
                )
            }
        }

        pickABubble(rule) {

            assertTrue(analyticsLibrary.logScreenCalledWith(AnalyticsScreens.BubblePicker))

            pressButton(0)
            assertTrue(analyticsLibrary.logButtonCalledWith(AnalyticsActions.OptionPressed))
        }
    }
}