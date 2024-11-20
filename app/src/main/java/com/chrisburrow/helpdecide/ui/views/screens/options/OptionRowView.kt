package com.chrisburrow.helpdecide.ui.views.screens.options

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.views.screens.HomeTags
import com.chrisburrow.helpdecide.utils.OptionObject

@Composable
fun OptionRowView(
    position: Int,
    option: OptionObject,
    removeOption: (OptionObject) -> Unit
) {

    Row(
        modifier = Modifier
            .testTag(OptionListTags.BASE_VIEW)
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

        IconButton(
            modifier = Modifier
                .testTag(OptionListTags.DELETE_TAG + position)
                .wrapContentSize(),
            onClick = {

                removeOption(option)
            },
        ) {
            Icon(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(end = 16.dp),
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete option: " + option.text
            )
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
