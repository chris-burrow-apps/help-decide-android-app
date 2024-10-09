package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ScreenUiState(
    val googleAnalyticsLoading: Boolean = true,
    val crashalyticsLoading: Boolean = true,
    val googleAnalyticsEnabled: Boolean = false,
    val crashalyticsEnabled: Boolean = false,
)

class SettingsViewModel(
    val analyticsLibrary: AnalyticsLibraryInterface
): AnalyticsViewModel(analyticsLibrary) {

    var uiState by mutableStateOf(ScreenUiState())
        private set

    fun refreshAnalytics() {

        viewModelScope.launch {

            val analyticsLibraryState =
                analyticsLibrary.getCrashalyticsState().combine(analyticsLibrary.getAnalyticsState()) { crashState, analyticsState ->

                    uiState = uiState.copy(
                        crashalyticsEnabled = crashState,
                        googleAnalyticsEnabled = analyticsState,

                        crashalyticsLoading = false,
                        googleAnalyticsLoading = false,
                    )
                }

            analyticsLibraryState.launchIn(this)
        }
    }

    fun toggleGoogleAnalytics(toggled: Boolean) {

        uiState = uiState.copy(googleAnalyticsEnabled = toggled)

        viewModelScope.launch {

            analyticsLibrary.setAnalyticsState(toggled)
        }
    }

    fun toggleCrashalytics(toggled: Boolean) {

        uiState = uiState.copy(crashalyticsEnabled = toggled)

        viewModelScope.launch {

            analyticsLibrary.setCrashalyticsState(toggled)
        }
    }
}