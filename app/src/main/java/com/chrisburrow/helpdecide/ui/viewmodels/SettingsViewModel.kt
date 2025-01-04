package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.ui.libraries.preferences.DecisionTypeLookup
import com.chrisburrow.helpdecide.ui.libraries.preferences.PreferencesLibraryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ScreenUiState(
    val googleAnalyticsEnabled: Boolean = false,
    val googleAnalyticsLoading: Boolean = true,
    val crashalyticsEnabled: Boolean = false,
    val crashalyticsLoading: Boolean = true,
    val alwaysAskEnabled: Boolean = false,
    val alwaysAskLoading: Boolean = true,
    val decisionTypeLoading: Boolean = true,
    val decisionType: String = "",
    val versionName: String = "",
) {
}

class SettingsViewModel(
    val analyticsLibrary: AnalyticsLibraryInterface,
    val preferencesLibrary: PreferencesLibraryInterface,
    val decisionTypeLookup: DecisionTypeLookup,
): AnalyticsViewModel(analyticsLibrary) {

    private val _viewState = MutableStateFlow(
        ScreenUiState()
    )

    val view: StateFlow<ScreenUiState> = _viewState.asStateFlow()

    fun refreshAnalytics() {

        viewModelScope.launch {

            val defaultDecisionTypeKey = preferencesLibrary.checkDefaultDecisionOption()

            _viewState.value = _viewState.value.copy(
                crashalyticsEnabled = analyticsLibrary.getCrashalyticsState(),
                googleAnalyticsEnabled = analyticsLibrary.getAnalyticsState(),
                alwaysAskEnabled = preferencesLibrary.alwaysAskDecisionDialog(),
                decisionType = decisionTypeLookup.getDecisionTypeTitle(defaultDecisionTypeKey),

                crashalyticsLoading = false,
                googleAnalyticsLoading = false,
                alwaysAskLoading = false,

                versionName = preferencesLibrary.checkVersionName()
            )
        }
    }

    fun toggleGoogleAnalytics(toggled: Boolean) {

        viewModelScope.launch {

            _viewState.value = _viewState.value.copy(googleAnalyticsEnabled = toggled)

            analyticsLibrary.setAnalyticsState(toggled)
        }
    }

    fun toggleCrashalytics(toggled: Boolean) {

        viewModelScope.launch {

            _viewState.value = _viewState.value.copy(crashalyticsEnabled = toggled)

            analyticsLibrary.setCrashalyticsState(toggled)
        }
    }

    fun toggleAlwaysAsk(toggled: Boolean) {

        viewModelScope.launch {

            _viewState.value = _viewState.value.copy(alwaysAskEnabled = toggled)

            preferencesLibrary.alwaysAskDecisionOption(toggled)
        }
    }
}