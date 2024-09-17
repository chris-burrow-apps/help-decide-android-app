package com.chrisburrow.helpdecide.ui.views.screens.settings

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
import com.chrisburrow.helpdecide.utils.SettingsRow

class SettingsListTags {

    companion object {

        const val ROW_VIEW = "RowView"
        const val BASE_VIEW = "BaseView"
        const val TITLE_TAG = "TitleOption"
        const val DESCRIPTION_TAG = "DescriptionOption"
    }
}

@Composable
fun SettingsList(
    options: List<SettingsRow>,
) {

    LazyColumn(modifier = Modifier.fillMaxWidth()) {

        itemsIndexed(options) { position, option ->

            SettingsRowView(position = position, option = option)

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