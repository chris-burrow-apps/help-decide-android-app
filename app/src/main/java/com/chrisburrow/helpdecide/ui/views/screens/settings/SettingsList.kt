package com.chrisburrow.helpdecide.ui.views.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.chrisburrow.helpdecide.utils.SettingsBooleanRow
import com.chrisburrow.helpdecide.utils.SettingsRow
import com.chrisburrow.helpdecide.utils.SettingsStringRow


@Composable
fun SettingsList(
    options: List<SettingsRow>,
) {

    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .testTag(SettingsListTags.BASE_VIEW)
    ) {

        itemsIndexed(options) { position, option ->

            when(option) {

                is SettingsBooleanRow -> {

                    SettingsBooleanView(position, option)
                }
                is SettingsStringRow -> {

                    SettingsStringView(position, option)
                }
            }

            if(position < options.size - 1) {

                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondary)
                )
            }
        }
    }
}