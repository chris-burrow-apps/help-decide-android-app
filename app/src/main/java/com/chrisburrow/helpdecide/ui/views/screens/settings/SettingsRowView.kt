package com.chrisburrow.helpdecide.ui.views.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.utils.SettingsBooleanRow
import com.chrisburrow.helpdecide.utils.SettingsRow
import com.chrisburrow.helpdecide.utils.SettingsStringRow

class SettingsListTags {

    companion object {

        const val ROW_VIEW = "RowView"
        const val BASE_VIEW = "BaseView"
        const val TITLE_TAG = "TitleOption"
        const val DESCRIPTION_TAG = "DescriptionOption"
        const val SWITCH_TAG = "SwitchOption"
        const val LOADING_TAG = "LoadingView"
    }
}

@Composable
private fun TitleDescriptionView(position: Int, option: SettingsRow) {

    Text(
        modifier = Modifier
            .testTag("${SettingsListTags.TITLE_TAG}$position")
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        text = option.title,
        fontWeight = FontWeight.Bold,

    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(
        modifier = Modifier
            .testTag("${SettingsListTags.DESCRIPTION_TAG}$position")
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary,
        text = option.description
    )
}

@Composable
fun SettingsBooleanView(position: Int, option: SettingsBooleanRow) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .testTag(SettingsListTags.ROW_VIEW + position)
            .padding(15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .weight(0.8f)
                .alpha(if(option.enabled) 1.0f else 0.6f)
        ) {

            TitleDescriptionView(position, option)
        }

        Column(
            modifier = Modifier
                .weight(0.2f)
                .padding(start = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if(option.loading) {

                CircularProgressIndicator(
                    modifier = Modifier.testTag(SettingsListTags.LOADING_TAG + position),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            } else {

                Switch(
                    modifier = Modifier.testTag(SettingsListTags.SWITCH_TAG + position),
                    checked = option.switchPosition,
                    enabled = option.enabled,
                    onCheckedChange = {
                        option.switchPosition = it
                        option.toggled(it)
                    },
                    thumbContent =
                    {
                        Icon(
                            imageVector = if (option.switchPosition) Icons.Filled.Check else Icons.Filled.Clear,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun SettingsStringView(position: Int, option: SettingsStringRow) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .testTag(SettingsListTags.ROW_VIEW + position)
            .padding(15.dp)
            .pointerInput(Unit) { // Using clicked blocks the scroll logic
                detectTapGestures(onTap = {

                    option.clicked()
                })
            },
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1.0f)
        ) {

            TitleDescriptionView(position, option)
        }
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
                    switchPosition = false,
                ) { },
                SettingsBooleanRow(
                    title = "Option 2",
                    description = "Description 2",
                    switchPosition = true
                ) { },
                SettingsBooleanRow(
                    title = "Option 3",
                    description = "Description 3",
                    switchPosition = false,
                    loading = true
                ) { },
                SettingsStringRow(
                    title = "Option 4",
                    description = "Description 4",
                ),
            )
        )
    }
}