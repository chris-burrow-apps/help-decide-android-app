package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.utils.OptionObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class HomeViewState(
    val voiceButton: Boolean = false,
    val options: List<OptionObject> = listOf(),
    val clearAllShown: Boolean = false,
    val decideOption: Boolean = false,
    val emptyView: Boolean = true,
)

data class HomeDialogState(
    val addOption: Boolean = false,
    val voiceOption: Boolean = false,

    val showOption: Boolean = false,
    val showWheelOption: Boolean = false,
    val defaultChoice: Boolean = false,
    val settings: Boolean = false,
    val deleteAll: Boolean = false,
)

class HomeViewModel(
    val analyticsLibrary: AnalyticsLibraryInterface,
    isSpeechCompatible: Boolean = false,
    initialOptions: List<OptionObject> = listOf(),
): AnalyticsViewModel(analyticsLibrary) {

    var view by mutableStateOf(HomeViewState(voiceButton = isSpeechCompatible, options = initialOptions))
        private set

    var dialogs by mutableStateOf(HomeDialogState())
        private set

    fun addOption(option: OptionObject) {

        view = view.copy(options = view.options.plus(option))

        checkButtonsState()
    }

    fun deleteOption(option: OptionObject) {

        view = view.copy(options = view.options.minus(option))

        checkButtonsState()
    }

    fun clearOptions() {

        view = view.copy(options = listOf())

        checkButtonsState()
    }

    private fun checkButtonsState() {

        checkDecideEnabled()
        checkEmptyShown()
        checkClearAllShown()
    }

    private fun checkDecideEnabled() {

        view = view.copy(decideOption = view.options.isNotEmpty() && view.options.size > 1)
    }

    private fun checkEmptyShown() {

        view = view.copy(emptyView = view.options.isEmpty())
    }

    private fun checkClearAllShown() {

        view = view.copy(clearAllShown = view.options.isNotEmpty())
    }
}