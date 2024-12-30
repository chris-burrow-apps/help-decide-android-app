package com.chrisburrow.helpdecide.ui.viewmodels

import com.chrisburrow.helpdecide.MainDispatcherRule
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.utils.OptionObject
import com.google.firebase.installations.time.SystemClock
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class DecideWheelViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun defaultValues() {

        val option = OptionObject(text = "option 1")
        val options = listOf(option)

        val viewModel = DecideWheelViewModel(MockAnalyticsLibrary(), options)

        assertNull(viewModel.uiState.value.decidedOption)
        assertEquals(options, viewModel.uiState.value.options)
        assertEquals(SpinAnimationState.IDLE, viewModel.uiState.value.wheelSpinning)
    }

    @Test
    fun spinTheWheel() {

        val optionOne = OptionObject(text = SystemClock.getInstance().currentTimeMillis().toString())
        val optionTwo = OptionObject(text = SystemClock.getInstance().currentTimeMillis().toString())

        val options = listOf(optionOne, optionTwo)

        val viewModel = DecideWheelViewModel(MockAnalyticsLibrary(), options)

        viewModel.spinTheWheel()

        assertEquals(SpinAnimationState.SPINNING, viewModel.uiState.value.wheelSpinning)
    }

    @Test
    fun chooseOption() {

        val expectedText = "example option"
        val option = OptionObject(text = expectedText)

        val options = listOf(option)
        val viewModel = DecideWheelViewModel(MockAnalyticsLibrary(), options)

        viewModel.spinTheWheel()

        viewModel.chooseOption()

        assertEquals(option, viewModel.uiState.value.decidedOption)
        assertEquals(SpinAnimationState.COMPLETE, viewModel.uiState.value.wheelSpinning)
    }
}