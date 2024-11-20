package com.chrisburrow.helpdecide.ui.views.screens.options

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.utils.OptionObject

class OptionListTags {

    companion object {

        const val BASE_VIEW = "BaseView"
        const val TEXT_TAG = "TextOption"
        const val DELETE_TAG = "DeleteOption"
    }
}

@Composable
fun OptionList(
    modifier: Modifier,
    options: List<OptionObject>,
    onDeleteClicked: (OptionObject) -> Unit
) {

    LazyColumn(modifier = modifier
        .fillMaxWidth()
        .background(MaterialTheme.colorScheme.surface)
    ) {

        itemsIndexed(options) { position, option ->

            OptionRowView(position = position, option = option) {

                onDeleteClicked(option)
            }

            if(position < options.size - 1) {

                HorizontalDivider(
                    modifier = Modifier
                        .padding(start = 15.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    thickness = 1.dp)
            }
        }
    }
}