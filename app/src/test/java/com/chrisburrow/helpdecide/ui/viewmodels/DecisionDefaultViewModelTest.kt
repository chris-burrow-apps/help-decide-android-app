package com.chrisburrow.helpdecide.ui.viewmodels

import com.chrisburrow.helpdecide.MainDispatcherRule
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.preferences.MockPreferencesLibrary
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class DecisionDefaultViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun defaultView() {

        val options = linkedMapOf("testKey1" to "Test Option 1", "testKey2" to "Test Option 2")

        val viewModel = DecisionDefaultViewModel(
            analyticsLibrary = MockAnalyticsLibrary(),
            preferencesLibrary = MockPreferencesLibrary(),
            options = options,
        )

        assertEquals(options.keys.first(), viewModel.uiState.value.currentlySelectedKey)
        assertEquals(options, viewModel.uiState.value.options)
    }

    @Test
    fun updateSelectedOption() {

        val newSelectedKey = "testKey2"
        val newSelectedValue = "Test Option 2"
        val options = linkedMapOf("testKey1" to "Test Option 1", newSelectedKey to newSelectedValue)

        val viewModel = DecisionDefaultViewModel(
            analyticsLibrary = MockAnalyticsLibrary(),
            preferencesLibrary = MockPreferencesLibrary(),
            options = options,
        )

        assertEquals(options.keys.first(), viewModel.uiState.value.currentlySelectedKey)

        viewModel.setDecisionOption(newSelectedKey)

        assertEquals(newSelectedKey, viewModel.uiState.value.currentlySelectedKey)
        assertEquals(newSelectedValue, viewModel.uiState.value.currentlySelectedValue)
    }

    @Test
    fun saveUserOption() {

        val newSelectedKey = "testKey2"
        val newSelectedValue = "Test Option 2"
        val options = linkedMapOf("testKey1" to "Test Option 1", newSelectedKey to newSelectedValue)

        val preferencesLibrary = MockPreferencesLibrary()

        val viewModel = DecisionDefaultViewModel(
            analyticsLibrary = MockAnalyticsLibrary(),
            preferencesLibrary = preferencesLibrary,
            options = options,
        )

        viewModel.setDecisionOption(newSelectedKey)

        assertEquals("", preferencesLibrary.defaultDecisionOption)

        viewModel.saveUserOption()

        assertEquals(newSelectedKey, preferencesLibrary.defaultDecisionOption)
    }

    @Test
    fun refreshDefaultDecision() {

        val newSelectedKey = "testKey2"
        val newSelectedValue = "Test Option 2"
        val options = linkedMapOf("testKey1" to "Test Option 1", newSelectedKey to newSelectedValue)

        val preferencesLibrary = MockPreferencesLibrary()

        val viewModel = DecisionDefaultViewModel(
            analyticsLibrary = MockAnalyticsLibrary(),
            preferencesLibrary = preferencesLibrary,
            options = options,
        )

        assertFalse(preferencesLibrary.checkDefaultDecisionOptionCalled)
        assertEquals(options.keys.first(), viewModel.uiState.value.currentlySelectedKey)

        viewModel.refreshDefaultDecision()

        assertTrue(preferencesLibrary.checkDefaultDecisionOptionCalled)
        assertEquals(options.keys.first(), viewModel.uiState.value.currentlySelectedKey)
    }
}