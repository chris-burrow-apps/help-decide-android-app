package com.chrisburrow.helpdecide.ui.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
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

@ThemePreviews
@Composable
fun OptionTextListPreview() {

    HelpDecideTheme {

        OptionList(options = listOf(OptionObject(text = "Option 1"), OptionObject(text = "Option 2"))) {}
    }
}