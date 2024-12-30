package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.lifecycle.viewModelScope
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.utils.OptionObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class SpinAnimationState {
    IDLE, SPINNING, COMPLETE
}

data class DecideWheelState(
    val options: List<OptionObject> = listOf(),
    var decidedOption: OptionObject? = null,
    val wheelSpinning: SpinAnimationState = SpinAnimationState.IDLE,
    val numberOfSegments: Int = 0,
    var targetRotation: Float = 0f,
    val removeEnabled: Boolean = false,
    val doneEnabled: Boolean = false,
) {
}

class DecideWheelViewModel(
    val analyticsLibrary: AnalyticsLibraryInterface,
    val options: List<OptionObject>
): AnalyticsViewModel(analyticsLibrary) {

    private val _uiState = MutableStateFlow(DecideWheelState(
        options = options.shuffled(),
        numberOfSegments = options.size
    ))
    val uiState = _uiState.asStateFlow()

    fun chooseOption() {

        val segmentSize = 360f / _uiState.value.numberOfSegments
        val normalisedRotation = _uiState.value.targetRotation % 360f
        val reversePosition = (normalisedRotation / segmentSize).toInt()

        viewModelScope.launch {

            _uiState.value = _uiState.value.copy(
                wheelSpinning = SpinAnimationState.COMPLETE,
                decidedOption = _uiState.value.options[_uiState.value.numberOfSegments - reversePosition - 1],
                doneEnabled = true,
                removeEnabled = true
            )
        }
    }

    fun spinTheWheel() {

        if(uiState.value.wheelSpinning != SpinAnimationState.SPINNING) {

            viewModelScope.launch {

                val rotateXTimes = (3 * 360)
                val nextRotation = rotateXTimes + ((10..350).random()).toFloat()

                _uiState.value = _uiState.value.copy(
                    wheelSpinning = SpinAnimationState.SPINNING,
                    targetRotation = _uiState.value.targetRotation + nextRotation,
                    doneEnabled = false,
                    removeEnabled = false
                )
            }
        }
    }
}