package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.utils.OptionObject

class DecideWheelViewModel(
    analyticsLibrary: AnalyticsLibraryInterface,
    options: List<OptionObject>
): AnalyticsViewModel(analyticsLibrary) {

    var options by mutableStateOf(options)
        private set

    var decidedOption by mutableStateOf("...")
        private set

    fun chooseOption(index: Int) {

        decidedOption = options[index].text
    }
}