package com.chrisburrow.helpdecide.ui.viewmodels

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class AddOptionViewModelTest {

    @Test
    fun defaultView() {

        val viewModel = AddOptionViewModel()

        val defaultValue = ""

        assertEquals(defaultValue, viewModel.optionText)
        assertFalse(viewModel.clearEnabled)
        assertFalse(viewModel.saveEnabled)
    }

    @Test
    fun clearEnabledWithText() {

        val viewModel = AddOptionViewModel()

        val modifiedText = "Option 1"
        viewModel.onTextChanged(modifiedText)

        assertEquals(modifiedText, viewModel.optionText)
        assertTrue(viewModel.clearEnabled)
        assertTrue(viewModel.saveEnabled)
    }

    @Test
    fun clearDisabledWithEmptyText() {

        val viewModel = AddOptionViewModel()

        val emptyText = ""
        viewModel.onTextChanged(emptyText)

        assertEquals(emptyText, viewModel.optionText)
        assertFalse(viewModel.clearEnabled)
        assertFalse(viewModel.saveEnabled)
    }

    @Test
    fun onTextCleared() {

        val viewModel = AddOptionViewModel(initialText = "Option 1")

        viewModel.onTextCleared()

        assertEquals("", viewModel.optionText)
        assertFalse(viewModel.clearEnabled)
        assertFalse(viewModel.saveEnabled)
    }

    @Test
    fun removeExtraWhiteSpaces() {

        val viewModel = AddOptionViewModel()

        val modifiedText = "Option 1"
        viewModel.onTextChanged("          $modifiedText            ")

        assertEquals(modifiedText, viewModel.optionText)
    }
}