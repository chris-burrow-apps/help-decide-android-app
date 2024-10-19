package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.utils.OptionObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DecideWheelState(
    val options: List<OptionObject> = listOf(),
    val decidedOption: OptionObject = OptionObject("", ""),
)

class DecideWheelViewModel(
    analyticsLibrary: AnalyticsLibraryInterface,
    options: List<OptionObject>
): AnalyticsViewModel(analyticsLibrary) {

    private val _uiState = MutableStateFlow(DecideWheelState(options = options))
    val uiState = _uiState.asStateFlow()

    fun chooseOption(index: Int) {

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                decidedOption = _uiState.value.options[index]
            )
        }
    }
}