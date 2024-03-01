package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class AddOptionViewModel(initialText: String = ""): ViewModel() {

    var optionText by mutableStateOf(initialText)
        private set

    var saveEnabled by  mutableStateOf(false)
        private set

    var clearEnabled by mutableStateOf(false)
        private set

    fun onTextCleared() {

        optionText = ""

        saveEnabled = false
        clearEnabled = false
    }

    fun onTextChanged(text: String) {

        optionText = text.trim()

        saveEnabled = text.isNotEmpty()
        clearEnabled = text.isNotEmpty()
    }
}