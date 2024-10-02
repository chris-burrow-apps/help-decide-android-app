package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.utils.OptionObject
import com.chrisburrow.helpdecide.utils.RandomGenerator
import com.chrisburrow.helpdecide.utils.RandomNumberInterface

class DecisionViewModel(
    analyticsLibrary: AnalyticsLibraryInterface,
    private val randomGenerator: RandomNumberInterface = RandomGenerator(),
    options: List<OptionObject>
): AnalyticsViewModel(analyticsLibrary) {

    var options by mutableStateOf(options)
        private set

    var decidedOption by mutableStateOf(OptionObject(text = "???"))
        private set

    fun chooseOption() {

        if(options.isNotEmpty()) {

            val generateNumber = randomGenerator.generateNumber(options.size - 1)

            decidedOption = options[generateNumber]
        }
    }
}