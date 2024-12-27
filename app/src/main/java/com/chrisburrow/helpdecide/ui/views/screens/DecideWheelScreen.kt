package com.chrisburrow.helpdecide.ui.views.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.PreviewOptions
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.DecideWheelViewModel
import com.chrisburrow.helpdecide.ui.views.DecideSpinWheel

class DecideWheelTags {

    companion object {

        const val BASE_VIEW_TAG = "DecisionWheelDialog"
        const val DECISION_TEXT_TAG = "DecisionTextDialog"
        const val REMOVE_BUTTON_TAG = "ClearButtonDialog"
        const val DONE_BUTTON_TAG = "DoneButtonDialog"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecideWheelScreen(
    viewModel: DecideWheelViewModel,
    backPressed: () -> Unit,
    optionChosen: (String) -> Unit
) {

    val state = remember { viewModel.uiState }
    val uiState by state.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.testTag(PickABubbleTags.BASE_VIEW_TAG),
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    scrolledContainerColor = Color.Unspecified,
                    titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                title = {
                    Text(
                        stringResource(R.string.spin_the_wheel),
                        fontSize = 35.sp,
                        fontFamily = FontFamily.Cursive,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon =
                {
                    IconButton(onClick = {
                        backPressed()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }) { innerPadding ->

        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp * 0.8

        Box(
            modifier = Modifier
                .padding(top = innerPadding.calculateTopPadding())
                .fillMaxSize()
                .testTag(DecideWheelTags.BASE_VIEW_TAG)
        ) {

            DecideSpinWheel(
                modifier = Modifier.align(Alignment.Center),
                wheelSize = screenWidth.dp,
                numberOfSegments = uiState.options.size,
                stopped = {

                    viewModel.chooseOption(it)
                    
                    optionChosen(viewModel.uiState.value.decidedOption.id)
                }
            )
        }

        LaunchedEffect(Unit) {

            viewModel.logScreenView(AnalyticsScreens.WHEEL)
        }
    }
}

@ThemePreviews
@Composable
fun DecideSpinWheelPreview() {

    HelpDecideTheme {

        DecideWheelScreen(
            DecideWheelViewModel(MockAnalyticsLibrary(), PreviewOptions()),
            backPressed = {},
            optionChosen = {},
        )
    }
}