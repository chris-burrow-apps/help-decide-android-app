package com.chrisburrow.helpdecide.ui

import android.content.res.Configuration
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.tooling.preview.Preview
import com.chrisburrow.helpdecide.utils.OptionObject

@Preview(
    apiLevel = 33,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
)
@Preview(
    apiLevel = 33,
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
annotation class ThemePreviews

fun PreviewOptions() : List<OptionObject> {

    val options = mutableListOf<OptionObject>()

    for (i in 1 until 20) {
        options.add(OptionObject(text = "Option $i"))
    }

    return options
}

@Composable
fun backgroundGradientBrush() = Brush.verticalGradient(colors = listOf(MaterialTheme.colorScheme.background,MaterialTheme.colorScheme.surfaceVariant))