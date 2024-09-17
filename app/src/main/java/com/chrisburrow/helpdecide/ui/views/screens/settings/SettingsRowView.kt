package com.chrisburrow.helpdecide.ui.views.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.utils.SettingsBooleanRow
import com.chrisburrow.helpdecide.utils.SettingsRow

@Composable
fun SettingsRowView(
    position: Int,
    option: SettingsRow,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag(SettingsListTags.BASE_VIEW)
            .background(MaterialTheme.colorScheme.surface)
            .testTag(SettingsListTags.ROW_VIEW)
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        when(option) {

            is SettingsBooleanRow -> {

                SettingsBooleanView(position, option)
            }
            else -> {

                TitleDescriptionView(position, option)
            }
        }
    }
}

@Composable
private fun TitleDescriptionView(position: Int, option: SettingsRow) {

    Text(
        modifier = Modifier
            .testTag("${SettingsListTags.TITLE_TAG}$position")
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        text = option.title
    )

    Text(
        modifier = Modifier
            .testTag("${SettingsListTags.DESCRIPTION_TAG}$position")
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        text = option.description
    )
}

@Composable
private fun SettingsBooleanView(position: Int, option: SettingsRow) {

    Column {

        TitleDescriptionView(position, option)
    }

    Column {


    }
}

@ThemePreviews
@Composable
fun SettingsTextListPreview() {

    HelpDecideTheme {

        SettingsList(
            options = listOf(
                SettingsBooleanRow(
                    title = "Option 1",
                    description = "Description 1",
                    false
                ) { }
            )
        )
    }
}