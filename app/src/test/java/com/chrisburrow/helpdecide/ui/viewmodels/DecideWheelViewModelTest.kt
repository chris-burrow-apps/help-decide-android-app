package com.chrisburrow.helpdecide.ui.viewmodels

import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.utils.OptionObject
import junit.framework.TestCase.assertEquals
import org.junit.Test

class DecideWheelViewModelTest {

    @Test
    fun defaultValues() {

        val options = listOf(OptionObject(text = "option 1"))

        val viewModel = DecideWheelViewModel(MockAnalyticsLibrary(), options)

        assertEquals("In progress", viewModel.decidedOption)
        assertEquals(options, viewModel.options)
    }

    @Test
    fun chooseOption() {

        val expectedText = "example option"

        val options = listOf(OptionObject(text = expectedText))
        val viewModel = DecideWheelViewModel(MockAnalyticsLibrary(), options)

        viewModel.chooseOption(0)

        assertEquals(expectedText, viewModel.decidedOption)
    }
}