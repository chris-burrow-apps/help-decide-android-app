package com.chrisburrow.helpdecide.ui.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp


object SpinTheWheelConfig {

    interface SpinWheelColors {
        @Composable
        fun frameColor(): State<Color>
        @Composable
        fun frameDotColor(): State<Color>
        @Composable
        fun selectorColor(): State<Color>
        @Composable
        fun backgroundColors(): State<List<Color>>
        @Composable
        fun textColors(): State<List<Color>>
    }

    @Immutable
    class DefaultSpinWheelColors(
        private val frameColor: Color,
        private val frameDotColor: Color,
        private val selectorColor: Color,
        private val backgroundColors: List<Color>,
        private val textColors: List<Color>,
    ) : SpinWheelColors {

        @Composable
        override fun frameColor(): State<Color> {
            return rememberUpdatedState(frameColor)
        }

        @Composable
        override fun frameDotColor(): State<Color> {
            return rememberUpdatedState(frameDotColor)
        }

        @Composable
        override fun selectorColor(): State<Color> {
            return rememberUpdatedState(selectorColor)
        }

        @Composable
        override fun backgroundColors(): State<List<Color>> {
            return rememberUpdatedState(backgroundColors)
        }

        @Composable
        override fun textColors(): State<List<Color>> {
            return rememberUpdatedState(textColors)
        }
    }

    interface SpinWheelDimensions {
        @Composable
        fun spinWheelSize(): State<Dp>
    }

    @Immutable
    class DefaultSpinWheelDimensions(
        private val spinWheelSize: Dp,
    ) : SpinWheelDimensions {

        @Composable
        override fun spinWheelSize(): State<Dp> {
            return rememberUpdatedState(spinWheelSize)
        }
    }
}