package com.chrisburrow.helpdecide.ui.views.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.AddOptionViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.DecideWheelViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.DecisionViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.GeneralDialogConfig
import com.chrisburrow.helpdecide.ui.viewmodels.GeneralDialogViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.HomeViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.PermissionsViewModel
import com.chrisburrow.helpdecide.ui.views.dialogs.AddOptionDialog
import com.chrisburrow.helpdecide.ui.views.dialogs.DecideWheelDialog
import com.chrisburrow.helpdecide.ui.views.dialogs.DecisionDefaultDialog
import com.chrisburrow.helpdecide.ui.views.dialogs.DecisionDialog
import com.chrisburrow.helpdecide.ui.views.dialogs.GeneralDialog
import com.chrisburrow.helpdecide.ui.views.dialogs.SettingsDialog
import com.chrisburrow.helpdecide.ui.views.screens.options.OptionList
import com.chrisburrow.helpdecide.utils.OptionObject
import com.chrisburrow.helpdecide.utils.speechtotext.SpeechToText


class HomeTags {

    companion object {

        const val BASE_VIEW_TAG = "HomeView"
        const val ADD_TEXT_TAG = "HomeAddTextButton"
        const val ADD_VOICE_TAG = "HomeAddVoiceButton"
        const val CLEAR_ALL_TAG = "ClearAllButton"
        const val SETTINGS_TAG = "HomeSettingsButton"
        const val DECIDE_BUTTON_TAG = "HomeDecideFAB"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    analyticsLibrary: AnalyticsLibraryInterface,
    model: HomeViewModel = HomeViewModel(
        analyticsLibrary,
        isSpeechCompatible = false
    )
) {

    val viewModel = remember { model }

    Scaffold(
        modifier = Modifier.testTag(HomeTags.BASE_VIEW_TAG),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    actionIconContentColor = Color.Unspecified,
                    navigationIconContentColor = Color.Unspecified,
                    scrolledContainerColor = Color.Unspecified,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                title = {
                    Text(
                        stringResource(id = R.string.app_name),
                        fontSize = 35.sp,
                        fontFamily = FontFamily.Cursive,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                actions = {

                    if(viewModel.view.clearAllShown) {

                        IconButton(
                            modifier = Modifier
                                .testTag(HomeTags.CLEAR_ALL_TAG)
                                .wrapContentSize(),
                            onClick = {

                                viewModel.logButtonPressed(AnalyticsActions.Clear)
                                viewModel.showDeleteAllDialog()
                            }
                        ) {
                            Icon(
                                tint = Color(MaterialTheme.colorScheme.secondary.toArgb()),
                                painter = painterResource(R.drawable.delete_icon),
                                contentDescription = stringResource(R.string.clear_all))
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(
                        modifier = Modifier
                            .testTag(HomeTags.SETTINGS_TAG)
                            .wrapContentSize(),
                        onClick = {

                            viewModel.showSettingsDialog()
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.settings_icon),
                            contentDescription = stringResource(R.string.settings))
                    }

                    IconButton(
                        modifier = Modifier.testTag(HomeTags.ADD_TEXT_TAG),
                        onClick = {

                            viewModel.showAddDialog()
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.text_icon),
                            contentDescription = stringResource(R.string.add_text_option))
                    }

                    if(viewModel.view.voiceButton) {

                        IconButton(
                            modifier = Modifier.testTag(HomeTags.ADD_VOICE_TAG),
                            onClick = {
                                viewModel.logButtonPressed(AnalyticsActions.Voice)
                                viewModel.showVoiceDialog()
                            },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.voice_icon),
                                contentDescription = stringResource(id = R.string.add_voice_option)
                            )
                        }
                    }
                },
                floatingActionButton = {
                    Button(
                        modifier = Modifier
                            .defaultMinSize(minWidth = 56.dp, minHeight = 56.dp)
                            .testTag(HomeTags.DECIDE_BUTTON_TAG),
                        enabled = viewModel.view.decideOption,
                        shape = CircleShape,
                        onClick = {

                            viewModel.logButtonPressed(AnalyticsActions.Decide)
                            viewModel.showDefaultDialog()
                        },
                    ){
                        Text(
                            text = stringResource(id = R.string.decide),
                        )
                    }
                }
            )
        }


    ) { innerPadding ->

        OptionList(
            modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
            options = viewModel.view.options,
            onDeleteClicked = { viewModel.deleteOption(it) }
        )

        if(viewModel.view.emptyView) {

            EmptyHomeInstructions(modifier = Modifier.padding(top = innerPadding.calculateTopPadding()))
        }

        if(viewModel.dialogs.addOption) {

            AddOptionDialog(
                model = AddOptionViewModel(analyticsLibrary),
                optionSaved = {

                    viewModel.hideAddDialog()
                    viewModel.addOption(OptionObject(text = it))
                },
                optionCancelled = {

                    viewModel.hideAddDialog()
                }
            )
        }

        if(viewModel.dialogs.voiceOption) {

            SpeechToText(response = {

                viewModel.addOption(OptionObject(text = it))
                viewModel.hideVoiceDialog()
            }, cancelled = {

                viewModel.hideVoiceDialog()
            })
        }

        if(viewModel.dialogs.showOption) {

            DecisionDialog(
                DecisionViewModel(
                    analyticsLibrary = analyticsLibrary,
                    options = viewModel.view.options
                ),
                removePressed = { option ->

                    viewModel.hideDecisionDialog()
                    viewModel.deleteOption(option)
                },
                donePressed = {

                    viewModel.hideDecisionDialog()
                }
            )
        }

        if(viewModel.dialogs.showWheelOption) {

            DecideWheelDialog(
                DecideWheelViewModel(
                    analyticsLibrary = analyticsLibrary,
                    options = viewModel.view.options
                ),
                removePressed = { option ->

                    viewModel.hideWheelDecisionDialog()
                    viewModel.deleteOption(option)
                },
                dismissPressed = {

                    viewModel.hideWheelDecisionDialog()
                }
            )
        }

        if(viewModel.dialogs.defaultChoice) {

            DecisionDefaultDialog(
                analyticsLibrary = analyticsLibrary,
                previouslySelected = 0,
                selected = { position ->

                    viewModel.hideDefaultDialog()

                    if(position == 0) {

                        viewModel.showWheelDecisionDialog()
                    } else if(position == 1) {

                        viewModel.showDecisionDialog()
                    }
                }
            )
        }

        if(viewModel.dialogs.deleteAll) {

            GeneralDialog(
                viewModel = GeneralDialogViewModel(
                    configuration = GeneralDialogConfig(
                        screenName = AnalyticsScreens.RemoveAll,
                        description = stringResource(id = R.string.confirm_delete_desc),
                        confirmText = stringResource(id = R.string.delete_all_button),
                        confirmPressed = {
                            viewModel.clearOptions()
                            viewModel.hideDeleteAllDialog()
                        },
                        cancelText = stringResource(id = R.string.cancel),
                        cancelPressed = {
                            viewModel.hideDeleteAllDialog()
                        },
                    ),
                    analyticsLibrary = analyticsLibrary,
                )
            )
        }

        if(viewModel.dialogs.settings) {

            SettingsDialog(model = PermissionsViewModel(analyticsLibrary)) {

                viewModel.hideSettingsDialog()
            }
        }
    }

    LaunchedEffect(Unit) {

        viewModel.logScreenView(AnalyticsScreens.Home)
    }
}

@Composable
fun EmptyHomeInstructions(modifier: Modifier) {

    Surface(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = stringResource(R.string.got_a_few_ideas_but_undecided_which_to_pick)
            )

            val buildTypeText = buildAnnotatedString {
                append("-  ")
                appendInlineContent(id = "manualAddIcon")
                append("  Add option by typing")
                append("\n\n")
                append("-  ")
                appendInlineContent(id = "voiceAddIcon")
                append("  Add option by voice")

            }

            val inlineContentMap = mapOf(
                "manualAddIcon" to InlineTextContent(
                    Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.text_icon),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = ""
                    )
                },
                "voiceAddIcon" to InlineTextContent(
                    Placeholder(20.sp, 20.sp, PlaceholderVerticalAlign.TextCenter)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.voice_icon),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = ""
                    )
                }
            )

            Text(
                text = buildTypeText,
                inlineContent = inlineContentMap,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,

            )
        }
    }
}

@ThemePreviews
@Composable
fun HomeScreenPreview() {

    HelpDecideTheme {

        val analyticsLibrary = MockAnalyticsLibrary()
        HomeScreen(analyticsLibrary = MockAnalyticsLibrary(), HomeViewModel(analyticsLibrary, isSpeechCompatible = true))
    }
}