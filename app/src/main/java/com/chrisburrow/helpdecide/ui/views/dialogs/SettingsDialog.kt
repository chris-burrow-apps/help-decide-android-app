package com.chrisburrow.helpdecide.ui.views.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.SettingsViewModel
import com.chrisburrow.helpdecide.ui.views.screens.settings.SettingsList
import com.chrisburrow.helpdecide.utils.SettingsBooleanRow

class SettingsDialogTags {

    companion object {

        const val BASE_VIEW_TAG = "SettingsDialog"
        const val TITLE_VIEW_TAG = "SettingsTitleDialog"
        const val DONE_BUTTON_TAG = "DoneButtonDialog"
    }
}

@Composable
fun SettingsDialog(
    viewModel: SettingsViewModel,
    onDismissRequested: () -> Unit
) {
    val uiState = remember { viewModel.uiState }

    Dialog(
        onDismissRequest = {
            onDismissRequested()
        },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {

        Surface(
            modifier = Modifier.testTag(SettingsDialogTags.BASE_VIEW_TAG),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier.testTag(SettingsDialogTags.TITLE_VIEW_TAG),
                    text = stringResource(id = R.string.settings),
                    fontSize = 35.sp,
                    fontFamily = FontFamily.Cursive,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                SettingsList(options = listOf(

                    SettingsBooleanRow(
                        stringResource(R.string.analytics),
                        stringResource(R.string.analytics_desc),
                        enabled = uiState.googleAnalyticsEnabled,
                        loading = uiState.googleAnalyticsLoading
                    ) { toggled ->

                        viewModel.toggleGoogleAnalytics(toggled)
                    },
                    SettingsBooleanRow(
                        stringResource(R.string.crashalytics),
                        stringResource(R.string.crashalytics_desc),
                        enabled = uiState.crashalyticsEnabled,
                        loading = uiState.crashalyticsLoading
                    ) { toggled ->

                        viewModel.toggleCrashalytics(toggled)
                    }
                ))
                Spacer(modifier = Modifier.height(16.dp))
                ElevatedButton(
                    modifier = Modifier
                        .testTag(SettingsDialogTags.DONE_BUTTON_TAG),
                    onClick = {

                        viewModel.logButtonPressed(AnalyticsActions.Done)
                        onDismissRequested()
                    },
                ) {

                    Text(stringResource(R.string.done))
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }



    LaunchedEffect(Unit) {

        viewModel.logScreenView(AnalyticsScreens.Settings)

        viewModel.refreshAnalytics()
    }
}

@ThemePreviews
@Composable
fun SettingsDialogPreview() {

    HelpDecideTheme {

        SettingsDialog(SettingsViewModel(MockAnalyticsLibrary())) {}
    }
}