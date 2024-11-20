package com.chrisburrow.helpdecide.ui.viewmodels

import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.preferences.MockPreferencesLibrary
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Test

class DecisionDefaultViewModelTest {

    @Test
    fun defaultView() {

        val currentlySelectedKey = ""
        val options = hashMapOf("testKey1" to "Test Option 1", "testKey2" to "Test Option 2")

        val viewModel = DecisionDefaultViewModel(
            analyticsLibrary = MockAnalyticsLibrary(),
            preferencesLibrary = MockPreferencesLibrary(),
            options = options,
            currentlySelectedKey = currentlySelectedKey
        )


        assertEquals(currentlySelectedKey, viewModel.uiState.value.currentlySelected)
        assertEquals(options, viewModel.uiState.value.options)
    }


}