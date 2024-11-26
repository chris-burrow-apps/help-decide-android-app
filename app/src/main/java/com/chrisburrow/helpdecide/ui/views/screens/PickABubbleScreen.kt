package com.chrisburrow.helpdecide.ui.views.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.PreviewOptions
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.PickABubbleViewModel

class PickABubbleTags  {

    companion object {

        const val BASE_VIEW_TAG = "BubbleView"
        const val BUBBLE_BUTTON_BASE_TAG = "BubbleButtonView"
        const val BUBBLE_BUTTON_CLICK_TAG = "BubbleButton"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickABubbleScreen(
    model: PickABubbleViewModel,
    optionPressed: (String) -> Unit,
    backPressed: () -> Unit
) {

    val state = remember { model.state }
    val view = state.collectAsState()

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    var screenHeight = configuration.screenHeightDp

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
                        stringResource(R.string.pick_a_bubble),
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
        },

    ) { innerPadding ->

        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(top = innerPadding.calculateTopPadding())
        ) {

            screenHeight -= innerPadding.calculateTopPadding().value.toInt()

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag(PickABubbleTags.BUBBLE_BUTTON_BASE_TAG)
                    .background(MaterialTheme.colorScheme.surface)
            ) {

                view.value.circles.forEach { circle ->

                    Button(
                        onClick = {
                            model.chooseOption(circle.position)
                            optionPressed(model.state.value.optionPressed!!.id)
                        },
                        modifier = Modifier
                            .testTag(PickABubbleTags.BUBBLE_BUTTON_CLICK_TAG + circle.position)
                            .size((circle.radius * 2).dp)
                            .offset((circle.x - circle.radius).dp, (circle.y - circle.radius).dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color.White, Color.DarkGray)
                                ),
                                shape = CircleShape
                            ),
                        colors = ButtonDefaults.buttonColors(),
                    ) {
                        Text("?")
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {

        model.logScreenView(AnalyticsScreens.BubblePicker)

        model.generateBubbles(
            screenWidth = screenWidth,
            screenHeight = screenHeight
        )
    }
}

@ThemePreviews
@Composable
fun PickABubbleScreenPreview() {

    HelpDecideTheme {

        PickABubbleScreen(
            model = PickABubbleViewModel(MockAnalyticsLibrary(), PreviewOptions()),
            optionPressed = {},
            backPressed = {}
        )
    }
}