package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
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
    val decisionTypeString: String = "",
    val versionName: String = "",
)

class SettingsViewModel(
    val analyticsLibrary: AnalyticsLibraryInterface,
    val preferencesLibrary: PreferencesLibraryInterface,
    val options: LinkedHashMap<String, String>,
): AnalyticsViewModel(analyticsLibrary) {

    private val _viewState = MutableStateFlow(
        ScreenUiState()
    )

    val view: StateFlow<ScreenUiState> = _viewState.asStateFlow()

    fun refreshPermissions() {

        viewModelScope.launch {

            val defaultDecisionTypeKey = preferencesLibrary.checkDefaultDecisionOption()
            val decisionType = options[defaultDecisionTypeKey] ?: options.values.first()

            _viewState.value = _viewState.value.copy(
                crashalyticsEnabled = analyticsLibrary.getCrashalyticsState(),
                googleAnalyticsEnabled = analyticsLibrary.getAnalyticsState(),
                alwaysAskEnabled = preferencesLibrary.shouldSkipDecisionType(),
                decisionTypeString = decisionType,

                crashalyticsLoading = false,
                googleAnalyticsLoading = false,
                alwaysAskLoading = false,
                decisionTypeLoading = false,

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

            preferencesLibrary.saveSkipDecisionType(toggled)
        }
    }
}