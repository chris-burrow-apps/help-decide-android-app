package com.chrisburrow.helpdecide.ui.views.dialogs

import android.provider.Settings.Global.getString
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.libraries.AnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.StorageLibrary
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.AddOptionViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.SettingsViewModel
import com.chrisburrow.helpdecide.ui.views.screens.settings.SettingsList
import com.chrisburrow.helpdecide.utils.SettingsBooleanRow
import com.google.firebase.ktx.BuildConfig

class SettingsDialogTags {

    companion object {

        const val BASE_VIEW_TAG = "SettingsDialog"
        const val TITLE_VIEW_TAG = "SettingsTitleDialog"
        const val DONE_BUTTON_TAG = "DoneButtonDialog"
    }
}

@Composable
fun SettingsDialog(
    model: SettingsViewModel = SettingsViewModel(
        AnalyticsLibrary(
            BuildConfig.DEBUG,
            StorageLibrary(LocalContext.current)
        )
    ),
    onDismissRequested: () -> Unit
) {

    val viewModel = remember { model }

    Dialog(
        onDismissRequest = { onDismissRequested() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {

        Surface(
            modifier = Modifier.testTag(AddDialogTags.BASE_VIEW_TAG),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.testTag(SettingsDialogTags.TITLE_VIEW_TAG),
                    text = stringResource(id = R.string.settings)
                )
                Spacer(modifier = Modifier.height(16.dp))
                SettingsList(options = listOf(

                    SettingsBooleanRow(
                        stringResource(R.string.analytics),
                        stringResource(R.string.analytics_desc),
                        enabled = viewModel.googleAnalyticsEnabled,
                        loading = viewModel.googleAnalyticsLoading
                    ) { toggled ->

                        viewModel.toggleGoogleAnalytics(toggled)
                    },
                    SettingsBooleanRow(
                        stringResource(R.string.crashalytics),
                        stringResource(R.string.crashalytics_desc),
                        enabled = viewModel.crashalyticsEnabled,
                        loading = viewModel.crashalyticsLoading
                    ) { toggled ->

                        viewModel.toggleCrashalytics(toggled)
                    }
                ))
                TextButton(
                    modifier = Modifier
                        .testTag(SettingsDialogTags.DONE_BUTTON_TAG),
                    onClick = { onDismissRequested() },
                ) {

                    Text(stringResource(R.string.done))
                }
            }
        }

        LaunchedEffect(Unit) {

            viewModel.refreshAnalytics()
        }
    }
}

@ThemePreviews
@Composable
fun SettingsDialogPreview() {

    HelpDecideTheme {

        SettingsDialog(onDismissRequested = {})
    }
}