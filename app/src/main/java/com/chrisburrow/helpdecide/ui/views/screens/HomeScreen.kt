package com.chrisburrow.helpdecide.ui.views.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.NavigationDialogItem
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.HomeViewModel
import com.chrisburrow.helpdecide.ui.views.screens.options.OptionList
import com.chrisburrow.helpdecide.utils.OptionObject


class HomeTags {

    companion object {

        const val BASE_VIEW_TAG = "HomeView"
        const val ADD_TEXT_TAG = "HomeAddTextButton"
        const val ADD_VOICE_TAG = "HomeAddVoiceButton"
        const val CLEAR_ALL_TAG = "ClearAllButton"
        const val SETTINGS_TAG = "HomeSettingsButton"
        const val DECIDE_BUTTON_TAG = "HomeDecideFAB"
        const val EMPTY_VIEW_TAG = "EmptyView"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    model: HomeViewModel
) {

    val state = remember { model.view }
    val view = state.collectAsState()

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

                    if(view.value.clearAllShown) {

                        IconButton(
                            modifier = Modifier
                                .testTag(HomeTags.CLEAR_ALL_TAG)
                                .wrapContentSize(),
                            onClick = {

                                model.logButtonPressed(AnalyticsActions.Clear)
                                navController.navigate(NavigationDialogItem.DeleteAll.route)
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
                            .testTag(HomeTags.ADD_TEXT_TAG)
                            .weight(0.25f),
                        onClick = {

                            navController.navigate(NavigationDialogItem.AddOption.route)
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.text_icon),
                            contentDescription = stringResource(R.string.add_text_option))
                    }

                    if(view.value.voiceButton) {

                        IconButton(

                            modifier = Modifier
                                .testTag(HomeTags.ADD_VOICE_TAG)
                                .weight(0.25f),
                            onClick = {

                                model.logButtonPressed(AnalyticsActions.Voice)
                                navController.navigate(NavigationDialogItem.SpeechToText.route)
                            },
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.voice_icon),
                                contentDescription = stringResource(id = R.string.add_voice_option)
                            )
                        }
                    }

                    IconButton(
                        modifier = Modifier
                            .testTag(HomeTags.SETTINGS_TAG)
                            .wrapContentSize()
                            .weight(0.25f),
                        onClick = {

                            navController.navigate(NavigationDialogItem.Settings.route)
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.settings_icon),
                            contentDescription = stringResource(R.string.settings))
                    }
                },
                floatingActionButton = {
                    Button(
                        modifier = Modifier
                            .defaultMinSize(minWidth = 56.dp, minHeight = 56.dp)
                            .testTag(HomeTags.DECIDE_BUTTON_TAG),
                        enabled = view.value.decideOption,
                        shape = CircleShape,
                        onClick = {

                            if(view.value.options.size > 1) {

                                model.logButtonPressed(AnalyticsActions.Decide)
                                navController.navigate(NavigationDialogItem.DecideType.route)
                            } else {

                                navController.navigate(NavigationDialogItem.AddAnother.route)
                            }
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

        if(view.value.emptyView) {

            EmptyHomeInstructions(
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                isSpeechEnabled = view.value.voiceButton
            )
        } else {

            OptionList(
                modifier = Modifier.padding(top = innerPadding.calculateTopPadding()),
                options = view.value.options,
                onDeleteClicked = { model.deleteOption(it.id) }
            )
        }
    }

    LaunchedEffect(Unit) {

        model.logScreenView(AnalyticsScreens.Home)
    }
}

@Composable
fun EmptyHomeInstructions(modifier: Modifier, isSpeechEnabled: Boolean) {

    Surface(
        modifier = modifier
            .fillMaxSize()
            .testTag(HomeTags.EMPTY_VIEW_TAG)
    ) {

        Box {

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

                    if (isSpeechEnabled) {

                        append("\n\n")
                        append("-  ")
                        appendInlineContent(id = "voiceAddIcon")
                        append("  Add option by voice")
                    }
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

                Spacer(modifier = Modifier.height(30.dp))

                Icon(
                    modifier = Modifier.size(50.dp).rotate(-45f),
                    painter = painterResource(R.drawable.down_left_arrow),
                    contentDescription = ""
                )
            }
        }
    }
}

@ThemePreviews
@Composable
fun HomeScreenEmptyPreview() {

    HelpDecideTheme {

        val analyticsLibrary = MockAnalyticsLibrary()
        val navController = rememberNavController()

        HomeScreen(navController,
            HomeViewModel(
                analyticsLibrary = analyticsLibrary,
                isSpeechCompatible = true
            )
        )
    }
}

@ThemePreviews
@Composable
fun HomeScreenPreview() {

    HelpDecideTheme {

        val analyticsLibrary = MockAnalyticsLibrary()
        val navController = rememberNavController()

        HomeScreen(navController,
            HomeViewModel(
                analyticsLibrary = analyticsLibrary,
                isSpeechCompatible = true,
                initialOptions = listOf(
                    OptionObject(text = "Option 1"),
                    OptionObject(text = "Option 2")
                )
            )
        )
    }
}