package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.utils.OptionObject
import com.chrisburrow.helpdecide.utils.RandomGenerator
import com.chrisburrow.helpdecide.utils.RandomNumberInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class DecisionDialogState(
    val options: List<OptionObject> = listOf(),
    val decidedOption: OptionObject = OptionObject(text = ""),
)

class DecisionViewModel(
    analyticsLibrary: AnalyticsLibraryInterface,
    private val randomGenerator: RandomNumberInterface = RandomGenerator(),
    options: List<OptionObject>
): AnalyticsViewModel(analyticsLibrary) {

    private var _uiState = MutableStateFlow(DecisionDialogState(options = options))
    var uiState = _uiState.asStateFlow()

    fun chooseOption() {

        if(uiState.value.options.isNotEmpty()) {

            val generateNumber = randomGenerator.generateNumber(uiState.value.options.size - 1)

            _uiState.value = uiState.value.copy(
                decidedOption = uiState.value.options[generateNumber]
            )
        }
    }
}