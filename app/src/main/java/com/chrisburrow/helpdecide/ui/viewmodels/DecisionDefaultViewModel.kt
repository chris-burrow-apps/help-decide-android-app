package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.ui.libraries.preferences.PreferencesLibraryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


data class DecisionDefaultState(
    val options: LinkedHashMap<String, String> = linkedMapOf(),
    val currentlySelectedKey: String,
    val currentlySelectedValue: String,
)

class DecisionDefaultViewModel(
    val analyticsLibrary: AnalyticsLibraryInterface,
    val preferencesLibrary: PreferencesLibraryInterface,
    val options: LinkedHashMap<String, String>,
): AnalyticsViewModel(analyticsLibrary) {

    private val _uiState = MutableStateFlow(
        DecisionDefaultState(
            options = options,
            currentlySelectedKey = options.keys.first(),
            currentlySelectedValue = options.values.first()
        )
    )

    val uiState = _uiState.asStateFlow()

    fun setDecisionOption(selectedKey: String) {

        _uiState.value = uiState.value.copy(
            currentlySelectedKey = selectedKey,
            currentlySelectedValue = options[selectedKey]!!
        )
    }

    fun saveUserOption() {

        viewModelScope.launch {

            preferencesLibrary.saveDefaultDecisionOption(uiState.value.currentlySelectedKey)
        }
    }

    fun refreshDefaultDecision() {

        viewModelScope.launch {

            val savedPreferenceKey = preferencesLibrary.checkDefaultDecisionOption()

            val displayedKey = savedPreferenceKey.ifBlank {
                options.keys.first()
            }

            val displayedValue = if(savedPreferenceKey.isEmpty()) {
                options.values.first()
            } else {
                options[savedPreferenceKey]!!
            }

            _uiState.value = uiState.value.copy(
                currentlySelectedKey = displayedKey,
                currentlySelectedValue = displayedValue
            )
        }
    }
}