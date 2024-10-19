package com.chrisburrow.helpdecide.ui.viewmodels

import com.chrisburrow.helpdecide.MainDispatcherRule
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.utils.OptionObject
import junit.framework.TestCase.assertEquals
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

        assertEquals(OptionObject("", ""), viewModel.uiState.value.decidedOption)
        assertEquals(options, viewModel.uiState.value.options)
    }

    @Test
    fun chooseOption() {

        val expectedText = "example option"
        val option = OptionObject(text = expectedText)

        val options = listOf(option)
        val viewModel = DecideWheelViewModel(MockAnalyticsLibrary(), options)

        viewModel.chooseOption(0)

        assertEquals(option, viewModel.uiState.value.decidedOption)
    }
}