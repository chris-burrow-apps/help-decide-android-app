package com.chrisburrow.helpdecide.ui.viewmodels

import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.utils.OptionObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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

    private var _view = MutableStateFlow(HomeViewState(voiceButton = isSpeechCompatible, options = initialOptions))
    var view = _view.asStateFlow()

    fun addOption(option: OptionObject) {

        _view.value = view.value.copy(options = view.value.options.plus(option))

        checkButtonsState()
    }

    fun deleteOption(id: String) {

        _view.value = view.value.copy(options = view.value.options.filterNot { it.id == id })

        checkButtonsState()
    }

    fun clearOptions() {

        _view.value = view.value.copy(options = listOf())

        checkButtonsState()
    }

    private fun checkButtonsState() {

        checkDecideEnabled()
        checkEmptyShown()
        checkClearAllShown()
    }

    private fun checkDecideEnabled() {

        _view.value = view.value.copy(decideOption = view.value.options.isNotEmpty() && view.value.options.size > 1)
    }

    private fun checkEmptyShown() {

        _view.value = view.value.copy(emptyView = view.value.options.isEmpty())
    }

    private fun checkClearAllShown() {

        _view.value = view.value.copy(clearAllShown = view.value.options.isNotEmpty())
    }
}