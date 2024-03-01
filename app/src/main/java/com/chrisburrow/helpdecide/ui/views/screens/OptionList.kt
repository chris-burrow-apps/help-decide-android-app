package com.chrisburrow.helpdecide.ui.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.utils.OptionObject

class OptionListTags {

    companion object {

        const val BASE_VIEW = "BaseView"
        const val TEXT_TAG = "TextOption"
        const val DELETE_BUTTON_TAG = "DeleteButton"
    }
}
@Composable
fun OptionList(modifier: Modifier = Modifier, options: List<OptionObject>, onDeleteClicked: (OptionObject) -> Unit) {

    LazyColumn(modifier = modifier.fillMaxWidth()) {

        itemsIndexed(options) { position, option ->

            OptionsCard(position, option = option, onDeleteClicked)

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

@Composable
fun OptionsCard(position: Int, option: OptionObject, onDeleteClicked: (OptionObject) -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(OptionListTags.BASE_VIEW),
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

        Button(
            modifier = Modifier
                .testTag("${OptionListTags.DELETE_BUTTON_TAG}$position")
                .wrapContentSize(),
            onClick = { onDeleteClicked(option) },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        ) {
            Icon(
                modifier = Modifier.wrapContentSize(),
                painter = painterResource(id = R.drawable.delete_icon),
                contentDescription = "Delete Option $position",
                tint = Color(MaterialTheme.colorScheme.secondary.toArgb())
            )
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