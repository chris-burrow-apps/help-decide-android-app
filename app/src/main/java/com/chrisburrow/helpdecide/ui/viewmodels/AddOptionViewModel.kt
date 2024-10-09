package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface

data class AddOptionState(
    val optionText: String = "",
    val saveEnabled: Boolean = false,
    val clearEnabled: Boolean = false,
)

class AddOptionViewModel(
    analyticsLibrary: AnalyticsLibraryInterface,
    initialText: String = ""
): AnalyticsViewModel(analyticsLibrary) {

    var uiState by mutableStateOf(AddOptionState(optionText = initialText))
        private set

    fun onTextCleared() {

        uiState = uiState.copy(
            optionText = "",
            saveEnabled = false,
            clearEnabled = false
        )
    }

    fun onTextChanged(text: String) {

        uiState = uiState.copy(
            optionText = text.trim(),
            saveEnabled = text.isNotEmpty(),
            clearEnabled = text.isNotEmpty()
        )
    }
}