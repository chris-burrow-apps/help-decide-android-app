package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chrisburrow.helpdecide.ui.libraries.AnalyticsLibrary
import kotlinx.coroutines.launch

class SettingsViewModel(
    val analyticsLibrary: AnalyticsLibrary
): ViewModel() {

    var googleAnalyticsEnabled by mutableStateOf(false)
        private set

    var crashalyticsEnabled by mutableStateOf(false)
        private set

    init {

        viewModelScope.launch {

            analyticsLibrary.getCrashalyticsState().collect {

                crashalyticsEnabled = it
            }

            analyticsLibrary.getAnalyticsState().collect {

                googleAnalyticsEnabled = it
            }
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