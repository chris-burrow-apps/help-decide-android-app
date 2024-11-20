package com.chrisburrow.helpdecide.ui.views

import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.utils.RandomGenerator
import com.commandiron.spin_wheel_compose.SpinWheel
import com.commandiron.spin_wheel_compose.SpinWheelDefaults
import com.commandiron.spin_wheel_compose.state.rememberSpinWheelState

@Composable
fun DecideSpinWheel(
    size: Int,
    stopped: (Int) -> Unit
) {

    val numberOfWheelSegments = if(size < 8) { size } else { 8 }
    val allWheelSegments = List(size) { it + 1 }
    val shownWheelSegments = allWheelSegments.shuffled().subList(0, numberOfWheelSegments)

    val state = rememberSpinWheelState(
        pieCount = numberOfWheelSegments,
        delayMillis = 1000,
        durationMillis = 6000,
        rotationPerSecond = 1f,
        easing = FastOutSlowInEasing,
        startDegree = RandomGenerator().generateNumber(360).toFloat(),
    )

    SpinWheel(
        state = state,
        dimensions = SpinWheelDefaults.spinWheelDimensions(
            frameWidth = 10.dp,
            selectorWidth = 15.dp
        ),
        colors = SpinWheelDefaults.spinWheelColors(
            frameColor = MaterialTheme.colorScheme.surfaceVariant,
            dividerColor = MaterialTheme.colorScheme.inverseSurface,
            selectorColor = Color.Red,
            pieColors = List(numberOfWheelSegments) {
                if (it % 3 == 0) {
                    MaterialTheme.colorScheme.primary
                } else if (it % 2 == 0) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.tertiary
                }
            }
        )
    ) { index ->

        val textColour =
            if (index % 3 == 0) {
                MaterialTheme.colorScheme.onPrimary
            } else if (index % 2 == 0) {
                MaterialTheme.colorScheme.onSecondary
            } else {
                MaterialTheme.colorScheme.onTertiary
            }

        Text(
            "?",
            style = TextStyle(
                fontSize = 25.sp,
                fontFamily = FontFamily.Cursive,
                fontWeight = FontWeight.Bold
            ),
            color = textColour,
        )
    }

    LaunchedEffect(Unit) {

        state.spin {

            val position = shownWheelSegments[it] - 1

            stopped(position)
        }
    }
}

@ThemePreviews
@Composable
fun DecideSpinWheelPreview() {

    val context = LocalContext.current

    HelpDecideTheme {

        DecideSpinWheel(
            size = 20,
            stopped = {

                Toast.makeText(context, "Winning index: $it", Toast.LENGTH_LONG).show()
            }
        )
    }
}
