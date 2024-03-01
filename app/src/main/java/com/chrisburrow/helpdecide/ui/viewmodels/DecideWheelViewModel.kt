package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.chrisburrow.helpdecide.utils.OptionObject
import com.chrisburrow.helpdecide.utils.RandomGenerator
import com.chrisburrow.helpdecide.utils.RandomNumberInterface

class DecideWheelViewModel(
    options: List<OptionObject>
): ViewModel() {

    var options by mutableStateOf(options)
        private set

    var decidedOption by mutableStateOf("In progress")
        private set

    fun chooseOption(index: Int) {

        decidedOption = options[index].text
    }
}