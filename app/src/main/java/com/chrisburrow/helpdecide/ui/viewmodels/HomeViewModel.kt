package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.utils.OptionObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeViewState(
    val voiceButton: Boolean = false,
    val options: List<OptionObject> = listOf(),
    val clearAllShown: Boolean = false,
    val decideOption: Boolean = false,
    val emptyView: Boolean = true,
)

class HomeViewModel(
    val analyticsLibrary: AnalyticsLibraryInterface,
    isSpeechCompatible: Boolean = false,
    initialOptions: List<OptionObject> = listOf(),
): AnalyticsViewModel(analyticsLibrary) {

    private var _view = MutableStateFlow(
        HomeViewState(options = initialOptions, voiceButton = isSpeechCompatible)
    )

    var view = _view.asStateFlow()

    init {

        checkButtonsState()
    }

    fun addOption(option: OptionObject) {

        viewModelScope.launch {

            _view.value = _view.value.copy(options = _view.value.options.plus(option))
        }

        checkButtonsState()
    }

    fun deleteOption(id: String) {

        viewModelScope.launch {

            _view.value = _view.value.copy(options = _view.value.options.filterNot { it.id == id })

            checkButtonsState()
        }
    }

    fun clearOptions() {

        viewModelScope.launch {
            _view.value = _view.value.copy(options = listOf())
        }

        checkButtonsState()
    }

    private fun checkButtonsState() {

        checkDecideEnabled()
        checkEmptyShown()
        checkClearAllShown()
    }

    private fun checkDecideEnabled() {

        viewModelScope.launch {

            _view.value = _view.value.copy(decideOption = _view.value.options.isNotEmpty())
        }
    }

    private fun checkEmptyShown() {

        viewModelScope.launch {

            _view.value = _view.value.copy(emptyView = _view.value.options.isEmpty())
        }
    }

    private fun checkClearAllShown() {

        viewModelScope.launch {

            _view.value = _view.value.copy(clearAllShown = _view.value.options.isNotEmpty())
        }
    }
}