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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.PreviewOptions
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.DecisionViewModel
import com.chrisburrow.helpdecide.utils.RandomGenerator

class DecisionDialogTags {

    companion object {

        const val BASE_VIEW_TAG = "DecisionDialog"
        const val DECISION_TEXT_TAG = "DecisionTextDialog"
        const val REMOVE_BUTTON_TAG = "ClearButtonDialog"
        const val DONE_BUTTON_TAG = "DoneButtonDialog"
    }
}

@Composable
fun DecisionDialog(
    viewModel: DecisionViewModel,
    donePressed: () -> Unit = {},
    removePressed: (String) -> Unit = {}
) {

    Dialog(
        onDismissRequest = { },
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        )
    ) {

        val state = remember { viewModel.uiState }
        val uiState by state.collectAsStateWithLifecycle()

        Surface(
            modifier = Modifier.testTag(DecisionDialogTags.BASE_VIEW_TAG),
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
                    modifier = Modifier.testTag(DecisionDialogTags.DECISION_TEXT_TAG),
                    text = uiState.decidedOption.text
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    ElevatedButton(
                        modifier = Modifier
                            .testTag(DecisionDialogTags.REMOVE_BUTTON_TAG)
                            .weight(1.0f),
                        onClick = {
                            viewModel.logButtonPressed(AnalyticsActions.REMOVE_OPTION)
                            removePressed(uiState.decidedOption.id)
                        },
                    ) {

                        Text(text = stringResource(R.string.remove_option), textAlign = TextAlign.Center)
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    ElevatedButton(
                        modifier = Modifier
                            .testTag(DecisionDialogTags.DONE_BUTTON_TAG)
                            .align(Alignment.CenterVertically)
                            .weight(1.0f),
                        onClick = {
                            viewModel.logButtonPressed(AnalyticsActions.DONE)
                            donePressed()
                        },
                    ) {

                        Text(text = stringResource(R.string.done), textAlign = TextAlign.Center)
                    }
                    
                }
            }
        }

        LaunchedEffect(Unit) {

            viewModel.logScreenView(AnalyticsScreens.INSTANT)
            viewModel.chooseOption()
        }
    }
}

@ThemePreviews
@Composable
fun DecisionDialogPreview() {

    HelpDecideTheme {

        DecisionDialog(
            DecisionViewModel(
                MockAnalyticsLibrary(),
                RandomGenerator(),
                PreviewOptions()
            ),
            removePressed = {},
            donePressed = {}
        )
    }
}