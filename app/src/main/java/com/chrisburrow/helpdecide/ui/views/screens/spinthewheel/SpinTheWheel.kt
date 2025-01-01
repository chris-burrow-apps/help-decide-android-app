package com.chrisburrow.helpdecide.ui.views.screens.spinthewheel

import android.R.attr
import android.graphics.Paint
import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.rotate
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.chrisburrow.helpdecide.ui.PreviewOptions
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.DecideWheelState
import com.chrisburrow.helpdecide.ui.viewmodels.SpinAnimationState
import kotlin.math.cos
import kotlin.math.sin


@Composable
fun SpinTheWheel(
    viewState: DecideWheelState,
    finishedAnimating: () -> Unit
) {
    val rotation = remember { Animatable(0f) }

    val configuration = LocalConfiguration.current
    val wheelSize = configuration.screenWidthDp * 0.8

    val dimensions = SpinTheWheelConfig.DefaultSpinWheelDimensions(
        spinWheelSize = wheelSize.dp,
    )

    val colors = SpinTheWheelConfig.DefaultSpinWheelColors(
        frameColor = MaterialTheme.colorScheme.surfaceVariant,
        frameDotColor = MaterialTheme.colorScheme.inverseSurface,
        selectorColor = Color.Red,
        backgroundColors = List(viewState.numberOfSegments) {
            if (it % 3 == 0) {
                MaterialTheme.colorScheme.primary
            } else if (it % 2 == 0) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.tertiary
            }
        },
        textColors = List(viewState.numberOfSegments) {
            if (it % 3 == 0) {
                MaterialTheme.colorScheme.onPrimary
            } else if (it % 2 == 0) {
                MaterialTheme.colorScheme.onSecondary
            } else {
                MaterialTheme.colorScheme.onTertiary
            }
        }
    )

    LaunchedEffect(viewState.wheelSpinning) {

        if (viewState.wheelSpinning == SpinAnimationState.SPINNING) {

            rotation.animateTo(
                targetValue = viewState.targetRotation,
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = FastOutSlowInEasing
                )
            )

            finishedAnimating()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.wrapContentSize()
    ) {

        val backgroundColours = colors.backgroundColors().value
        val textColors = colors.textColors().value
        val frameColor = colors.frameColor().value
        val frameDotColor = colors.frameDotColor().value

        val lightGlowColor = frameDotColor.copy(alpha = 0.5f)

        Canvas(
            modifier = Modifier
                .size(dimensions.spinWheelSize().value)
                .graphicsLayer(rotationZ = rotation.value)
        ) {

            val canvasSize = size.minDimension
            val radius = canvasSize / 2
            val anglePerSegment = 360f / viewState.numberOfSegments

            drawBorder(
                radius,
                frameColor,
                viewState.numberOfSegments,
                canvasSize / 2,
                canvasSize / 2,
                lightGlowColor,
                frameDotColor
            )


            for (i in 0 until viewState.numberOfSegments) {

                val startAngle = (i * anglePerSegment) - 90

                drawSegment(
                    color = backgroundColours[i],
                    startAngle = startAngle,
                    sweepAngle = anglePerSegment,
                    size = size
                )

                // Draw text in each segment
                drawTextOnPath(
                    text = viewState.options[i].text,
                    radius = radius,
                    angle = startAngle + anglePerSegment / 2,
                    size = size,
                    textColor = textColors[i]
                )
            }
        }

        DrawCenterPointer(
            modifier = Modifier.align(Alignment.Center),
            colors = colors
        )
    }
}

// Helper function to draw individual segments
fun DrawScope.drawSegment(
    color: Color,
    startAngle: Float,
    sweepAngle: Float,
    size: Size,
) {
    val path = Path().apply {
        moveTo(size.width / 2, size.height / 2)
        arcTo(
            rect = Rect(Offset.Zero, size),
            startAngleDegrees = startAngle,
            sweepAngleDegrees = sweepAngle,
            forceMoveTo = false
        )
        close()
    }
    drawPath(path, color, style = Stroke(width = 2f))
    drawPath(path, color)
}

fun DrawScope.drawBorder(
    radius: Float,
    frameColor: Color,
    numberOfSegments: Int,
    centerX: Float,
    centerY: Float,
    lightGlowColor: Color,
    frameDotColor: Color
) {
    val buttonRadius = radius + 20.dp.value
    val anglePerButton = 360f / numberOfSegments
    val frameWidth = 20.dp.value

    drawCircle(
        color = frameColor,
        radius = radius,
        center = Offset(centerX, centerY),
        style = Stroke(width = 90f)
    )

    for (i in 0 until numberOfSegments) {

        val angleRad = Math.toRadians((i * anglePerButton).toDouble())
        val buttonCenterX = centerX + buttonRadius * cos(angleRad).toFloat()
        val buttonCenterY = centerY + buttonRadius * sin(angleRad).toFloat()

        drawCircle(
            color = lightGlowColor,
            radius = frameWidth - (frameWidth / 3),
            center = Offset(buttonCenterX, buttonCenterY)
        )

        drawCircle(
            color = frameDotColor,
            radius = frameWidth - (frameWidth / 2),
            center = Offset(buttonCenterX, buttonCenterY)
        )
    }
}

@Composable
fun DrawCenterPointer(
    modifier: Modifier,
    colors: SpinTheWheelConfig.SpinWheelColors,
) {

    val whiteColor = Color.White
    val blackColor = Color.Black

    val selectorColor = colors.selectorColor().value

    Canvas(modifier = modifier.wrapContentSize()) {

        val radius = 15.dp.toPx()
        val centerX = size.width / 2
        val centerY = size.height / 2

        val triangleHeight = 35.dp.toPx()

        val trianglePath = Path().apply {
            moveTo(centerX, centerY - radius - triangleHeight)
            lineTo(
                centerX - triangleHeight / 2f,
                centerY - radius + 15.dp.toPx()
            )
            lineTo(
                centerX + triangleHeight / 2f,
                centerY - radius + 15.dp.toPx()
            )
            close()
        }

        drawPath(
            path = trianglePath,
            color = selectorColor
        )

        withTransform({
        }) {
            drawIntoCanvas { canvas ->
                val shadowPaint = Paint().apply {
                    color = android.graphics.Color.WHITE
                    setShadowLayer(
                        45f,
                        0f,
                        10f,
                        blackColor.toArgb()
                    )
                }
                canvas.nativeCanvas.drawCircle(
                    centerX,
                    centerY,
                    radius + 5.dp.toPx(),
                    shadowPaint
                )
            }
        }

        drawCircle(
            color = whiteColor,
            radius = radius,
            center = Offset(centerX, centerY)
        )
    }
}

fun DrawScope.drawTextOnPath(
    text: String,
    radius: Float,
    angle: Float,
    size: Size,
    textColor: Color,
) {
    val centerX = size.width / 2
    val centerY = size.height / 2

    val substring = if(text.length < 12) text else "${text.substring(0, 12)}..."

    val x = centerX + radius / 1.7f * cos(Math.toRadians(angle.toDouble())).toFloat()
    val y = centerY + radius / 1.7f * sin(Math.toRadians(angle.toDouble())).toFloat()

    drawContext.canvas.rotate(angle, x, y)

    drawContext.canvas.nativeCanvas.drawText(
        substring,
        x,
        y,
        Paint().apply {
            color = textColor.toArgb()
            textSize = getDynamicTextSize(radius / 2, substring)
            textAlign = Paint.Align.CENTER
        }
    )

    drawContext.canvas.rotate(-angle, x, y)
}

fun getDynamicTextSize(width: Float, text: String) : Float {

    val paint = Paint()
    var textSize = 80f
    paint.textSize = textSize
    var textWidth: Float

    while (true) {
        textWidth = paint.measureText(text)
        if (textWidth <= width) {
            break
        }
        textSize -= 1f
        paint.textSize = textSize
    }

    return textSize
}

@ThemePreviews
@Composable
fun SpinTheWheelPreview() {

    val context = LocalContext.current

    HelpDecideTheme {

        SpinTheWheel(
            viewState = DecideWheelState(
                options = PreviewOptions(),
                numberOfSegments = PreviewOptions().size
            ),
            finishedAnimating = {

                Toast.makeText(context, "Finished spinning", Toast.LENGTH_LONG).show()
            }
        )
    }
}