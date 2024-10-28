package com.chrisburrow.helpdecide.ui.views.screens.options

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.utils.OptionObject

@Composable
fun OptionRowView(
    position: Int,
    option: OptionObject,
    removeOption: (OptionObject) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(OptionListTags.BASE_VIEW)
    ) {

        val swipeState = rememberSwipeToDismissBoxState()

        SwipeToDismissBox(
            state = swipeState,
            modifier = Modifier.testTag(OptionListTags.ROW_VIEW + position),
            backgroundContent = {
                DismissBackground(swipeState)
            },
            content = {
                OptionTextView(position = position, option = option)
            }
        )

        when (swipeState.currentValue) {
            SwipeToDismissBoxValue.StartToEnd, SwipeToDismissBoxValue.EndToStart -> {
                LaunchedEffect(swipeState.currentValue) {
                    removeOption(option)
                    swipeState.snapTo(SwipeToDismissBoxValue.Settled)
                }
            }

            SwipeToDismissBoxValue.Settled -> {
                // Nothing to do
            }
        }
    }
}


@Composable
fun OptionTextView(position: Int, option: OptionObject) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .weight(1.0f)
                .testTag(OptionListTags.TEXT_TAG + position),
            color = MaterialTheme.colorScheme.primary,
            text = option.text
        )
    }
}

@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {

    val color by animateColorAsState(
        targetValue = when (dismissState.targetValue) {
            SwipeToDismissBoxValue.Settled -> MaterialTheme.colorScheme.surface
            SwipeToDismissBoxValue.StartToEnd -> Color.Red
            SwipeToDismissBoxValue.EndToStart -> Color.Red
        },
        label = "swipe"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
    ) {

        // 4. Show the correct icon
        when (dismissState.targetValue) {
            SwipeToDismissBoxValue.StartToEnd -> {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(end = 16.dp),
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete"
                )
            }

            SwipeToDismissBoxValue.EndToStart -> {
                Icon(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp),
                    imageVector = Icons.Default.Delete,
                    contentDescription = "delete"
                )
            }

            SwipeToDismissBoxValue.Settled -> {
                // Nothing to do
            }
        }

    }
}

@ThemePreviews
@Composable
fun OptionTextListPreview() {

    HelpDecideTheme {

        OptionList(modifier = Modifier, options = listOf(OptionObject(text = "Option 1"), OptionObject(text = "Option 2"))) {}
    }
}
