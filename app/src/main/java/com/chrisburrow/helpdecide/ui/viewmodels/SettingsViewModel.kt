package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chrisburrow.helpdecide.ui.libraries.AnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.AnalyticsLibraryInterface
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch

class SettingsViewModel(
    val analyticsLibrary: AnalyticsLibraryInterface
): ViewModel() {

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
}