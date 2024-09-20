package com.chrisburrow.helpdecide.ui.views.dialogs

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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.DecideWheelViewModel
import com.chrisburrow.helpdecide.ui.views.DecideSpinWheel
import com.chrisburrow.helpdecide.utils.OptionObject

class DecideWheelDialogTags {

    companion object {

        const val BASE_VIEW_TAG = "DecisionWheelDialog"
        const val DECISION_TEXT_TAG = "DecisionTextDialog"
        const val CLEAR_BUTTON_TAG = "ClearButtonDialog"
        const val DONE_BUTTON_TAG = "DoneButtonDialog"
    }
}
@Composable
fun DecideWheelDialog(
    options: List<OptionObject>,
    model: DecideWheelViewModel = DecideWheelViewModel(options),
    dismissPressed: () -> Unit,
    clearPressed: () -> Unit
) {

    val viewModel = remember { model }

    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        onDismissRequest = { dismissPressed() },
    ) {

        Surface(
            modifier = Modifier.testTag(DecideWheelDialogTags.BASE_VIEW_TAG),
            shape = RoundedCornerShape(8.dp)) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(10.dp))

                DecideSpinWheel(
                    size = viewModel.options.size,
                    stopped = { viewModel.chooseOption(it) }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier.testTag(DecideWheelDialogTags.DECISION_TEXT_TAG),
                    color = MaterialTheme.colorScheme.primary,
                    text = viewModel.decidedOption
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    ElevatedButton(
                        modifier = Modifier
                            .testTag(DecideWheelDialogTags.CLEAR_BUTTON_TAG)
                            .weight(1.0f),
                        onClick = { clearPressed() },
                    ) {

                        Text(text = stringResource(R.string.clear))
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    ElevatedButton(
                        modifier = Modifier
                            .testTag(DecideWheelDialogTags.DONE_BUTTON_TAG)
                            .weight(1.0f),
                        onClick = { dismissPressed() },
                    ) {

                        Text(stringResource(R.string.done))
                    }
                }
            }
        }
    }
}

@ThemePreviews
@Composable
fun DecideSpinWheelPreview() {

    HelpDecideTheme {

        DecideWheelDialog(
            options = listOf(
                OptionObject(text = "Option 1"),
                OptionObject(text = "Option 2")
            ),
            dismissPressed = {},
            clearPressed = {},
        )
    }
}