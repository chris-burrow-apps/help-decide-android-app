package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.ui.libraries.preferences.DecisionTypeLookup
import com.chrisburrow.helpdecide.ui.libraries.preferences.PreferencesLibrary
import com.chrisburrow.helpdecide.ui.libraries.preferences.PreferencesLibraryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ScreenUiState(
    val googleAnalyticsEnabled: Boolean = false,
    val googleAnalyticsLoading: Boolean = true,
    val crashalyticsEnabled: Boolean = false,
    val crashalyticsLoading: Boolean = true,
    val alwaysAskEnabled: Boolean = true,
    val alwaysAskLoading: Boolean = true,
    val decisionTypeLoading: Boolean = true,
    val decisionType: String = "",
    val versionName: String = "",
) {
}

class SettingsViewModel(
    val analyticsLibrary: AnalyticsLibraryInterface,
    val preferencesLibrary: PreferencesLibraryInterface,
): AnalyticsViewModel(analyticsLibrary) {

    private var _uiState = MutableStateFlow(ScreenUiState())
    var uiState = _uiState.asStateFlow()

    fun refreshAnalytics() {

        viewModelScope.launch {

            _uiState.value = uiState.value.copy(
                crashalyticsEnabled = analyticsLibrary.getCrashalyticsState(),
                googleAnalyticsEnabled = analyticsLibrary.getAnalyticsState(),
                alwaysAskEnabled = preferencesLibrary.alwaysAskDecisionDialog(),
                decisionType = preferencesLibrary.checkDefaultDecisionOption(),

                crashalyticsLoading = false,
                googleAnalyticsLoading = false,
                alwaysAskLoading = false,

                versionName = preferencesLibrary.checkVersionName()
            )
        }
    }

    fun toggleGoogleAnalytics(toggled: Boolean) {

        _uiState.value = uiState.value.copy(googleAnalyticsEnabled = toggled)

        viewModelScope.launch {

            analyticsLibrary.setAnalyticsState(toggled)
        }
    }

    fun toggleCrashalytics(toggled: Boolean) {

        _uiState.value = uiState.value.copy(crashalyticsEnabled = toggled)

        viewModelScope.launch {

            analyticsLibrary.setCrashalyticsState(toggled)
        }
    }

    fun toggleAlwaysAsk(toggled: Boolean) {

        _uiState.value = uiState.value.copy(alwaysAskEnabled = toggled)

        viewModelScope.launch {

            preferencesLibrary.alwaysAskDecisionOption(toggled)
        }
    }
}