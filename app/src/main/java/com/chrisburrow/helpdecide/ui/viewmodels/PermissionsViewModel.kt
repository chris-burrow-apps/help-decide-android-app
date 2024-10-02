package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch

class PermissionsViewModel(
    val analyticsLibrary: AnalyticsLibraryInterface
): AnalyticsViewModel(analyticsLibrary) {

    var googleAnalyticsLoading : Boolean by mutableStateOf(true)
        private set

    var crashalyticsLoading : Boolean by mutableStateOf(true)
        private set

    var googleAnalyticsEnabled : Boolean by mutableStateOf(false)
        private set

    var crashalyticsEnabled : Boolean by mutableStateOf(false)
        private set

    fun refreshAnalytics() {

        viewModelScope.launch {

            val analyticsLibraryState =
                analyticsLibrary.getCrashalyticsState().combine(analyticsLibrary.getAnalyticsState()) { crashState, analyticsState ->

                    crashalyticsEnabled = crashState
                    googleAnalyticsEnabled = analyticsState

                    crashalyticsLoading = false
                    googleAnalyticsLoading = false
                }

            analyticsLibraryState.launchIn(this)
        }
    }

    fun toggleGoogleAnalytics(toggled: Boolean) {

        googleAnalyticsEnabled = toggled

        viewModelScope.launch {

            analyticsLibrary.setAnalyticsState(toggled)
        }
    }

    fun toggleCrashalytics(toggled: Boolean) {

        crashalyticsEnabled = toggled

        viewModelScope.launch {

            analyticsLibrary.setCrashalyticsState(toggled)
        }
    }

    fun settingsShown() {

        viewModelScope.launch {

            analyticsLibrary.settingsShown()
        }
    }
}