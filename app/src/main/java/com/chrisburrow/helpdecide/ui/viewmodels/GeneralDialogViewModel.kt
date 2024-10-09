package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface

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

    var uiState by mutableStateOf(configuration)
        private set

    fun onConfirmPressed() {

        logButtonPressed(uiState.confirmText)
        uiState.confirmPressed()
    }

    fun onCancelPressed() {

        logButtonPressed(uiState.cancelText)
        uiState.cancelPressed()
    }

    fun trackScreenView() {

        logScreenView(uiState.screenName)
    }
}