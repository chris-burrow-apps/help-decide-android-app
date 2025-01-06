package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.ui.libraries.preferences.DecisionTypeLookup
import com.chrisburrow.helpdecide.ui.libraries.preferences.PreferencesLibraryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class DecisionDefaultState(
    val doneButtonText: String,
    val options: LinkedHashMap<String, String> = linkedMapOf(),
    val currentlySelectedKey: String,
    val currentlySelectedValue: String,
    val dontAskAgainToggle: Boolean,
)

class DecisionDefaultViewModel(
    val analyticsLibrary: AnalyticsLibraryInterface,
    val preferencesLibrary: PreferencesLibraryInterface,
    val options: LinkedHashMap<String, String>,
    doneButtonText: String,
    val pressedDone: (String) -> Unit,
): AnalyticsViewModel(analyticsLibrary) {

    private val _uiState = MutableStateFlow(
        DecisionDefaultState(
            options = options,
            currentlySelectedKey = options.keys.first(),
            currentlySelectedValue = options.values.first(),
            dontAskAgainToggle = false,
            doneButtonText = doneButtonText,
        )
    )

    val uiState = _uiState.asStateFlow()

    fun setDecisionOption(selectedKey: String) {

        _uiState.value = uiState.value.copy(
            currentlySelectedKey = selectedKey,
            currentlySelectedValue = options.get(selectedKey) ?: options.values.first()
        )
    }

    fun toggleDoNotAskCheck(enabled: Boolean) {

        _uiState.value = _uiState.value.copy(dontAskAgainToggle = enabled)
    }

    fun saveUserOption() {

        viewModelScope.launch {

            preferencesLibrary.saveDefaultDecisionOption(uiState.value.currentlySelectedKey)
            preferencesLibrary.saveSkipDecisionType(uiState.value.dontAskAgainToggle)

            pressedDone(uiState.value.currentlySelectedKey)
        }
    }

    fun refreshDefaultDecision() {

        val options = _uiState.value.options

        viewModelScope.launch {

            val savedPreferenceKey = preferencesLibrary.checkDefaultDecisionOption()
            val dontAskAgainToggle = preferencesLibrary.shouldSkipDecisionType()

            val displayedKey = savedPreferenceKey.ifBlank {
                options.keys.first()
            }

            val displayedValue = if(savedPreferenceKey.isEmpty()) {
                options.values.first()
            } else {
                options[savedPreferenceKey]!!
            }

            _uiState.value = uiState.value.copy(
                dontAskAgainToggle = dontAskAgainToggle,
                currentlySelectedKey = displayedKey,
                currentlySelectedValue = displayedValue
            )
        }
    }
}