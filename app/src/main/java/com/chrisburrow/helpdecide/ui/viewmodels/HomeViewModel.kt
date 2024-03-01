package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.chrisburrow.helpdecide.utils.OptionObject

data class HomeViewState(
    val decideOption: Boolean = false,
    val voiceButton: Boolean = false,
    val options: List<OptionObject> = listOf(),
    val emptyView: Boolean = options.isEmpty(),
)

data class HomeDialogState(
    val addOption: Boolean = false,
    val voiceOption: Boolean = false,

    val showOption: Boolean = false,
    val showWheelOption: Boolean = false,
    val defaultChoice: Boolean = false
)

class HomeViewModel(
    isSpeechCompatible: Boolean = false,
    initialOptions: List<OptionObject> = listOf(),
): ViewModel() {

    var view by mutableStateOf(HomeViewState(voiceButton = isSpeechCompatible, options = initialOptions))
        private set

    var dialogs by mutableStateOf(HomeDialogState())
        private set

    init {

        checkDecideEnabled()
        checkEmptyShown()
    }

    fun addOption(option: OptionObject) {

        view = view.copy(options = view.options.plus(option))

        checkDecideEnabled()
        checkEmptyShown()
    }

    fun deleteOption(option: OptionObject) {

        view = view.copy(options = view.options.minus(option))

        checkDecideEnabled()
        checkEmptyShown()
    }

    fun clearOptions() {

        view = view.copy(options = listOf())

        checkDecideEnabled()
        checkEmptyShown()
    }

    private fun checkDecideEnabled() {

        view = view.copy(decideOption = view.options.isNotEmpty() && view.options.size > 1)
    }

    private fun checkEmptyShown() {

        view = view.copy(emptyView = view.options.isEmpty())
    }

    fun showAddDialog() {

        dialogs = dialogs.copy(addOption = true)
    }

    fun hideAddDialog() {

        dialogs = dialogs.copy(addOption = false)
    }

    fun showVoiceDialog() {

        dialogs = dialogs.copy(voiceOption = true)
    }

    fun hideVoiceDialog() {

        dialogs = dialogs.copy(voiceOption = false)
    }

    fun showDecisionDialog() {

        dialogs = dialogs.copy(showOption = true)
    }

    fun hideDecisionDialog() {

        dialogs = dialogs.copy(showOption = false)
    }

    fun showWheelDecisionDialog() {

        dialogs = dialogs.copy(showWheelOption = true)
    }

    fun hideWheelDecisionDialog() {

        dialogs = dialogs.copy(showWheelOption = false)
    }

    fun showDefaultDialog() {

        dialogs = dialogs.copy(defaultChoice = true)
    }

    fun hideDefaultDialog() {

        dialogs = dialogs.copy(defaultChoice = false)
    }
}