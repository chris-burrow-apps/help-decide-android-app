package com.chrisburrow.helpdecide.ui.viewmodels

import android.os.SystemClock
import com.chrisburrow.helpdecide.MainDispatcherRule
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.utils.OptionObject
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class PickABubbleViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun checkGeneratedOptions() {

        val optionOne = OptionObject(text = "Option 1")
        val optionTwo = OptionObject(text = "Option 2")
        val analyticsLibrary = MockAnalyticsLibrary()

        val options = listOf(optionOne, optionTwo)
        val viewModel = PickABubbleViewModel(analyticsLibrary, options)

        viewModel.generateBubbles(Int.MAX_VALUE, Int.MAX_VALUE)

        assertEquals(2, viewModel.state.value.circles.size)
    }

    @Test
    fun checkOptionPressed() {

        val optionOne = OptionObject(text = "Option 1")
        val optionTwo = OptionObject(text = "Option 2")
        val analyticsLibrary = MockAnalyticsLibrary()

        val options = listOf(optionOne, optionTwo)
        val viewModel = PickABubbleViewModel(analyticsLibrary, options)

        viewModel.chooseOption(0)

        assertEquals(optionOne, viewModel.state.value.optionPressed)
    }

    @Test
    fun analyticsOptionPressed() {

        val expectedText = "example option"
        val option = OptionObject(text = expectedText)
        val analyticsLibrary = MockAnalyticsLibrary()

        val options = listOf(option)
        val viewModel = PickABubbleViewModel(analyticsLibrary, options)

        viewModel.chooseOption(0)

        assertTrue(analyticsLibrary.logButtonCalledWith(AnalyticsActions.OptionPressed))
    }
}