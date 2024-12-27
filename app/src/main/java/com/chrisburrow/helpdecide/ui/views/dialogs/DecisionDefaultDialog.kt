@file:OptIn(ExperimentalMaterial3Api::class)

package com.chrisburrow.helpdecide.ui.views.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.preferences.MockPreferencesLibrary
import com.chrisburrow.helpdecide.ui.viewmodels.DecisionDefaultViewModel

class DecisionDefaultDialogTags {

    companion object {

        const val BASE_VIEW_TAG = "DecisionDefaultView"
        const val OPTION_CHOSEN_TAG = "DecisionChosenText"
        const val OPTION_ROW_TAG = "DecisionChosenRow"
        const val GO_BUTTON_TAG = "DecisionGoButton"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecisionDefaultDialog(
    viewModel: DecisionDefaultViewModel,
    selectedKey:(String) -> Unit
) {

    val state = remember { viewModel.uiState }
    val uiState by state.collectAsStateWithLifecycle()

    var expandedOptions by remember { mutableStateOf(false) }

    Dialog(
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false
        ),
        onDismissRequest = { },
    ) {

        Surface(
            modifier = Modifier.testTag(DecisionDefaultDialogTags.BASE_VIEW_TAG),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {

                Spacer(modifier = Modifier
                    .height(16.dp)
                    .fillMaxWidth())

                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .testTag(DecisionDefaultDialogTags.OPTION_CHOSEN_TAG)
                            .fillMaxWidth()
                            .wrapContentSize()
                            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                            .align(Alignment.CenterHorizontally),
                        expanded = expandedOptions,
                        onExpandedChange = {
                            expandedOptions = !expandedOptions
                        }
                    ) {
                        TextField(
                            modifier = Modifier
                                .menuAnchor(),
                            readOnly = true,
                            value = uiState.currentlySelectedValue,
                            onValueChange = { },
                            label = { Text(stringResource(R.string.how_do_you_want_to_decide)) },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expandedOptions
                                )
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = expandedOptions,
                            onDismissRequest = {
                                expandedOptions = false
                            }
                        ) {

                            val options = uiState.options

                            options.keys.forEachIndexed { position, key ->

                                DropdownMenuItem(
                                    modifier = Modifier.testTag(DecisionDefaultDialogTags.OPTION_ROW_TAG + position),
                                    text = {
                                        Text(
                                            text = options.get(key)!!
                                        )
                                    },
                                    onClick = {

                                        viewModel.setDecisionOption(key)
                                        expandedOptions = false
                                    }
                                )
                            }
                        }
                    }

                ElevatedButton(
                    modifier = Modifier
                        .testTag(DecisionDefaultDialogTags.GO_BUTTON_TAG)
                        .align(Alignment.CenterHorizontally)
                        .padding(8.dp),
                    onClick = {

                        viewModel.logButtonPressed(AnalyticsActions.GO)
                        viewModel.saveUserOption()

                        selectedKey(uiState.currentlySelectedKey)
                    },
                ) {

                    Text(
                        fontWeight = FontWeight.SemiBold,
                        text = stringResource(R.string.go)
                    )
                }
            }
        }
    }

    LaunchedEffect(Unit) {

        viewModel.logScreenView(AnalyticsScreens.DECISION_TYPE)
        viewModel.refreshDefaultDecision()
    }
}

@ThemePreviews
@Composable
fun DecisionDefaultBottomSheetPreview() {

    DecisionDefaultDialog(
        viewModel = DecisionDefaultViewModel(
            MockAnalyticsLibrary(),
            MockPreferencesLibrary(),
            linkedMapOf("optionOneKey" to "Option 1")
        ),
        selectedKey = {})
}