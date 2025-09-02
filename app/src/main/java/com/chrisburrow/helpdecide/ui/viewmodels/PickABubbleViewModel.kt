package com.chrisburrow.helpdecide.ui.viewmodels

import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.utils.OptionObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.random.Random

data class Bubble(
    val position: Int,
    val x: Float,
    val y: Float,
    val radius: Float,
    val optionId: String,
)

data class PickABubbleState(
    val options: List<OptionObject> = listOf(),
    val optionPressed: OptionObject? = null,
    val bubbles: List<Bubble> = listOf()
)

class PickABubbleViewModel(
    val analyticsLibrary: AnalyticsLibraryInterface,
    val options: List<OptionObject>
): AnalyticsViewModel(analyticsLibrary) {

    private val _uiState = MutableStateFlow(PickABubbleState(options = options))
    val state = _uiState.asStateFlow()

    fun chooseOption(position: Int) {

        logButtonPressed(AnalyticsActions.OPTION_PRESSED)

        _uiState.value = _uiState.value.copy(optionPressed = options[position])
    }

    fun generateBubbles(screenWidth: Int, screenHeight: Int) {

        val bubbles = mutableListOf<Bubble>()

        val circleCount = options.size

        for (position in 0 until circleCount) {

            var retryLoop = 0

            while (retryLoop <= 10) {

                val radius = Random.nextInt(screenWidth / 8, screenWidth / 4).toFloat()
                val x = Random.nextInt(radius.toInt(), (screenWidth - radius).toInt()).toFloat()
                val y = Random.nextInt(radius.toInt(), (screenHeight - radius).toInt()).toFloat()
                val candidateCircle = Bubble(
                    position = position,
                    x = x,
                    y = y,
                    radius = radius,
                    optionId = options[position].id
                )

                if (!bubbles.any { it.overlaps(candidateCircle) }) {

                    bubbles.add(candidateCircle)

                    break
                } else {

                    retryLoop++
                }
            }
        }

        _uiState.value = _uiState.value.copy(bubbles = bubbles)
    }

    private fun Bubble.overlaps(other: Bubble): Boolean {
        val distance = sqrt(
            (x - other.x).pow(2) +
                    (y - other.y).pow(2)
        )
        return distance < radius + other.radius
    }
}