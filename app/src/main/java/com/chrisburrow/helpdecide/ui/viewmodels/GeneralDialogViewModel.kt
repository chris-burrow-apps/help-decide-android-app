package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class GeneralDialogConfig(
    var screenName: String,
    var description: String,
    var confirmText: String,
    var confirmPressed: () -> Unit,
    var cancelText: String,
    var cancelPressed: () -> Unit
)

class GeneralDialogViewModel(
    configuration: GeneralDialogConfig,
    analyticsLibrary: AnalyticsLibraryInterface,
): AnalyticsViewModel(analyticsLibrary) {

    private var _uiState = MutableStateFlow(configuration)
    var uiState = _uiState.asStateFlow()

    fun onConfirmPressed() {

        logButtonPressed(uiState.value.confirmText)
        uiState.value.confirmPressed()
    }

    fun onCancelPressed() {

        logButtonPressed(uiState.value.cancelText)
        uiState.value.cancelPressed()
    }

    fun trackScreenView() {

        logScreenView(uiState.value.screenName)
    }
}