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

    var dialog by mutableStateOf(configuration)
        private set

    fun onConfirmPressed() {

        logButtonPressed(dialog.confirmText)
        dialog.confirmPressed()
    }

    fun onCancelPressed() {

        logButtonPressed(dialog.cancelText)
        dialog.cancelPressed()
    }

    fun trackScreenView() {

        logScreenView(dialog.screenName)
    }
}