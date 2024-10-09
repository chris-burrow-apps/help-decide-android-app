package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.utils.OptionObject

data class DecideWheelState(
    val options: List<OptionObject> = listOf(),
    val decidedOption: OptionObject = OptionObject("", ""),
)

class DecideWheelViewModel(
    analyticsLibrary: AnalyticsLibraryInterface,
    options: List<OptionObject>
): AnalyticsViewModel(analyticsLibrary) {

    var uiState by mutableStateOf(DecideWheelState(options = options))
        private set

    fun chooseOption(index: Int) {

        uiState = uiState.copy(
            decidedOption = uiState.options[index]
        )
    }
}