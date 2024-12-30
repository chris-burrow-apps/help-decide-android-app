package com.chrisburrow.helpdecide.ui.views.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.GeneralDialogConfig
import com.chrisburrow.helpdecide.ui.viewmodels.GeneralDialogViewModel

class GeneralDialogTags {

    companion object {

        const val BASE_VIEW_TAG = "GeneralDialog"
        const val DESCRIPTION_TAG = "GeneralDescDialog"
        const val CONFIRM_VIEW_TAG = "GeneralDialogConfirm"
        const val CANCEL_VIEW_TAG = "GeneralDialogCancel"
    }
}

@Composable
fun GeneralDialog(
    viewModel: GeneralDialogViewModel,
) {

    Dialog(
        onDismissRequest = { viewModel.onCancelPressed() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {

        val state = remember { viewModel.uiState }
        val uiState by state.collectAsState()

        Surface(
            modifier = Modifier.testTag(GeneralDialogTags.BASE_VIEW_TAG),
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 10.dp,
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier.testTag(GeneralDialogTags.DESCRIPTION_TAG),
                    text = uiState.description
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    ElevatedButton(
                        modifier = Modifier.testTag(GeneralDialogTags.CANCEL_VIEW_TAG).weight(1.0f),
                        onClick = {

                            viewModel.onCancelPressed()
                        },
                    ) {

                        Text(text = uiState.cancelText)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    ElevatedButton(
                        modifier = Modifier.testTag(GeneralDialogTags.CONFIRM_VIEW_TAG).weight(1.0f),
                        onClick = {

                            viewModel.onConfirmPressed()
                        },
                    ) {

                        Text(text = uiState.confirmText)
                    }
                }
            }
        }

        LaunchedEffect(Unit) {

            viewModel.trackScreenView()
        }
    }
}

@ThemePreviews
@Composable
fun GeneralDialogPreview() {

    HelpDecideTheme {

        GeneralDialog(
            viewModel = GeneralDialogViewModel(
                configuration = GeneralDialogConfig(
                    screenName = "",
                    description = "Description 1",
                    confirmText = "Confirm 1",
                    confirmPressed = {},
                    cancelText = "Cancel 1",
                    cancelPressed = {},
                ),
                analyticsLibrary = MockAnalyticsLibrary()
            ),
        )
    }
}