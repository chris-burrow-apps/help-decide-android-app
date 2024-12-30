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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.AddOptionViewModel

class AddDialogTags {

    companion object {

        const val BASE_VIEW_TAG = "OptionDialog"
        const val OPTION_TEXT_TAG = "OptionTextDialog"
        const val CANCEL_BUTTON_TAG = "CancelButtonDialog"
        const val CLEAR_BUTTON_TAG = "ClearButtonDialog"
        const val ADD_BUTTON_TAG = "AddButtonDialog"
    }
}

@Composable
fun AddOptionDialog(
    viewModel: AddOptionViewModel,
    optionSaved: (String) -> Unit = {},
    optionCancelled: () -> Unit = {}
) {
    val state = remember { viewModel.uiState }
    val uiState by state.collectAsStateWithLifecycle()

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    Dialog(
        onDismissRequest = { optionCancelled() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {

        Surface(
            modifier = Modifier.testTag(AddDialogTags.BASE_VIEW_TAG),
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
                TextField(
                    modifier = Modifier
                        .testTag(AddDialogTags.OPTION_TEXT_TAG)
                        .focusRequester(focusRequester),
                    value = uiState.optionText,
                    trailingIcon = {
                        IconButton(
                            modifier = Modifier.testTag(AddDialogTags.CLEAR_BUTTON_TAG),
                            enabled = uiState.clearEnabled,
                            onClick = {
                                viewModel.logButtonPressed(AnalyticsActions.CLEAR)
                                viewModel.onTextCleared()
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Clear,
                                contentDescription = stringResource(R.string.clear)
                            )
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onGo = { keyboardController?.hide() }
                    ),
                    onValueChange = {

                        viewModel.onTextChanged(it)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    ElevatedButton(
                        modifier = Modifier
                            .testTag(AddDialogTags.CANCEL_BUTTON_TAG)
                            .weight(1.0f),
                        onClick = {
                            viewModel.logButtonPressed(AnalyticsActions.CANCEL)
                            optionCancelled()
                        },
                    ) {

                        Text(stringResource(R.string.cancel))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    ElevatedButton(
                        modifier = Modifier
                            .testTag(AddDialogTags.ADD_BUTTON_TAG)
                            .weight(1.0f),
                        enabled = uiState.addEnabled,
                        onClick = {
                            viewModel.logButtonPressed(AnalyticsActions.ADD)
                            optionSaved(uiState.optionText.trim())
                        },
                    ) {

                        Text(text = stringResource(R.string.add))
                    }
                }
            }
        }

        LaunchedEffect(Unit) {

            viewModel.logScreenView(AnalyticsScreens.ADD_TEXT)
            focusRequester.requestFocus()
        }
    }
}

@ThemePreviews
@Composable
fun AddDialogPreview() {

    HelpDecideTheme {

        AddOptionDialog(
            viewModel = AddOptionViewModel(MockAnalyticsLibrary()),
            optionSaved = {},
            optionCancelled = {}
        )
    }
}