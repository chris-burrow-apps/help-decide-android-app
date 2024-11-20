package com.chrisburrow.helpdecide.ui.viewmodels

import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class AddOptionState(
    val optionText: String = "",
    val addEnabled: Boolean = false,
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
            addEnabled = false,
            clearEnabled = false
        )
    }

    fun onTextChanged(text: String) {

        _uiState.value = uiState.value.copy(
            optionText = text,
            addEnabled = text.trim().isNotEmpty(),
            clearEnabled = text.isNotEmpty()
        )
    }
}