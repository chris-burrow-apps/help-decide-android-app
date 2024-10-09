package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.utils.OptionObject
import com.chrisburrow.helpdecide.utils.RandomGenerator
import com.chrisburrow.helpdecide.utils.RandomNumberInterface

data class DecisionDialogState(
    val options: List<OptionObject> = listOf(),
    val decidedOption: OptionObject = OptionObject(text = ""),
)

class DecisionViewModel(
    analyticsLibrary: AnalyticsLibraryInterface,
    private val randomGenerator: RandomNumberInterface = RandomGenerator(),
    options: List<OptionObject>
): AnalyticsViewModel(analyticsLibrary) {

    var uiState by mutableStateOf(DecisionDialogState(options = options))
        private set

    fun chooseOption() {

        if(uiState.options.isNotEmpty()) {

            val generateNumber = randomGenerator.generateNumber(uiState.options.size - 1)

            uiState = uiState.copy(
                decidedOption = uiState.options[generateNumber]
            )
        }
    }
}