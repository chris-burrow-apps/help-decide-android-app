package com.chrisburrow.helpdecide.ui.views.screens.options

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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

        val context = LocalContext.current
        val dismissState = rememberSwipeToDismissBoxState(
            confirmValueChange = {
                when(it) {
                    SwipeToDismissBoxValue.Settled -> return@rememberSwipeToDismissBoxState false
                    else -> {
                        removeOption(option)
                        Toast.makeText(context, "Option removed", Toast.LENGTH_SHORT).show()
                    }
                }
                return@rememberSwipeToDismissBoxState true
            },
            positionalThreshold = { it * .25f }
        )
        SwipeToDismissBox(
            state = dismissState,
            modifier = Modifier.testTag(OptionListTags.ROW_VIEW + position),
            backgroundContent = { DismissBackground(dismissState) },
            content = {
                OptionTextView(position = position, option = option)
            }
        )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DismissBackground(dismissState: SwipeToDismissBoxState) {
    val color = when (dismissState.dismissDirection) {
        SwipeToDismissBoxValue.Settled -> Color.Transparent
        else -> Color(0xFFFF1744)
    }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color)
            .padding(12.dp, 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            Icons.Default.Delete,
            contentDescription = "delete"
        )
        Spacer(modifier = Modifier)
        Icon(
            Icons.Default.Delete,
            contentDescription = "delete"
        )
    }
}

@ThemePreviews
@Composable
fun OptionTextListPreview() {

    HelpDecideTheme {

        OptionList(modifier = Modifier, options = listOf(OptionObject(text = "Option 1"), OptionObject(text = "Option 2"))) {}
    }
}
