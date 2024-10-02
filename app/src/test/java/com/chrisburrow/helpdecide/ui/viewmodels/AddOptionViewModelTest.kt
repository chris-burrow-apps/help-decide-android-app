package com.chrisburrow.helpdecide.ui.viewmodels

import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class AddOptionViewModelTest {

    @Test
    fun defaultView() {

        val viewModel = AddOptionViewModel(MockAnalyticsLibrary())

        val defaultValue = ""

        assertEquals(defaultValue, viewModel.optionText)
        assertFalse(viewModel.clearEnabled)
        assertFalse(viewModel.saveEnabled)
    }

    @Test
    fun clearEnabledWithText() {

        val viewModel = AddOptionViewModel(MockAnalyticsLibrary())

        val modifiedText = "Option 1"
        viewModel.onTextChanged(modifiedText)

        assertEquals(modifiedText, viewModel.optionText)
        assertTrue(viewModel.clearEnabled)
        assertTrue(viewModel.saveEnabled)
    }

    @Test
    fun clearDisabledWithEmptyText() {

        val viewModel = AddOptionViewModel(MockAnalyticsLibrary())

        val emptyText = ""
        viewModel.onTextChanged(emptyText)

        assertEquals(emptyText, viewModel.optionText)
        assertFalse(viewModel.clearEnabled)
        assertFalse(viewModel.saveEnabled)
    }

    @Test
    fun onTextCleared() {

        val viewModel = AddOptionViewModel(MockAnalyticsLibrary(), initialText = "Option 1")

        viewModel.onTextCleared()

        assertEquals("", viewModel.optionText)
        assertFalse(viewModel.clearEnabled)
        assertFalse(viewModel.saveEnabled)
    }

    @Test
    fun removeExtraWhiteSpaces() {

        val viewModel = AddOptionViewModel(MockAnalyticsLibrary())

        val modifiedText = "Option 1"
        viewModel.onTextChanged("          $modifiedText            ")

        assertEquals(modifiedText, viewModel.optionText)
    }
}