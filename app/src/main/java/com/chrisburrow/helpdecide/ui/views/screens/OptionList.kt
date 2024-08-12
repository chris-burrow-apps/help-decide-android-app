package com.chrisburrow.helpdecide.ui.views.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismissBox
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
import com.chrisburrow.helpdecide.ui.views.DismissBackground
import com.chrisburrow.helpdecide.utils.OptionObject

class OptionListTags {

    companion object {

        const val ROW_VIEW = "RowView"
        const val BASE_VIEW = "BaseView"
        const val TEXT_TAG = "TextOption"
    }
}
@Composable
fun OptionList(
    modifier: Modifier = Modifier,
    options: List<OptionObject>,
    onDeleteClicked: (OptionObject) -> Unit
) {

    LazyColumn(modifier = modifier.fillMaxWidth()) {

        itemsIndexed(options) { position, option ->

            OptionRowView(position = position, option = option) {

                onDeleteClicked(option)
            }

            if(position < options.size - 1) {

                Spacer(
                    modifier = Modifier
                        .height(2.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondary)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionRowView(
    modifier: Modifier = Modifier,
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
            modifier = modifier.testTag(OptionListTags.ROW_VIEW + position),
            backgroundContent = { DismissBackground(dismissState)},
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
                .testTag("${OptionListTags.TEXT_TAG}$position")
                .fillMaxWidth()
                .padding(15.dp)
                .weight(1.0f),
            color = MaterialTheme.colorScheme.primary,
            text = option.text
        )
    }
}

@ThemePreviews
@Composable
fun OptionTextListPreview() {

    HelpDecideTheme {

        OptionList(options = listOf(OptionObject(text = "Option 1"), OptionObject(text = "Option 2"))) {}
    }
}