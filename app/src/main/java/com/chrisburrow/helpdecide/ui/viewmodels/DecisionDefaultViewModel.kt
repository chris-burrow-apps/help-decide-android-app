package com.chrisburrow.helpdecide.ui.viewmodels

import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.ui.libraries.preferences.PreferencesLibraryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


data class DecisionDefaultState(
    val options: HashMap<String, String> = hashMapOf(),
    val currentlySelected: String
)

class DecisionDefaultViewModel(
    val analyticsLibrary: AnalyticsLibraryInterface,
    val preferencesLibrary: PreferencesLibraryInterface,
    val options: HashMap<String, String>,
    currentlySelectedKey: String,
): AnalyticsViewModel(analyticsLibrary) {

    private val _uiState = MutableStateFlow(
        DecisionDefaultState(
            options = options,
            currentlySelected = currentlySelectedKey
        )
    )

    val uiState = _uiState.asStateFlow()


}