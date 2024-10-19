package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AddOptionState(
    val optionText: String = "",
    val saveEnabled: Boolean = false,
    val clearEnabled: Boolean = false,
)

class AddOptionViewModel(
    analyticsLibrary: AnalyticsLibraryInterface,
    initialText: String = ""
): AnalyticsViewModel(analyticsLibrary) {

    private var _uiState = MutableStateFlow(AddOptionState(optionText = initialText))
    var uiState = _uiState.asStateFlow()

    fun onTextCleared() {

        _uiState.value = uiState.value.copy(
            optionText = "",
            saveEnabled = false,
            clearEnabled = false
        )
    }

    fun onTextChanged(text: String) {

        _uiState.value = uiState.value.copy(
            optionText = text.trim(),
            saveEnabled = text.isNotEmpty(),
            clearEnabled = text.isNotEmpty()
        )
    }
}