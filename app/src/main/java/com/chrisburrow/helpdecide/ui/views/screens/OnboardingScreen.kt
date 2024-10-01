@file:OptIn(ExperimentalFoundationApi::class)

package com.chrisburrow.helpdecide.ui.views.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.OnboardingPage
import com.chrisburrow.helpdecide.ui.viewmodels.OnboardingViewModel


class OnboardingTags {

    companion object {

        const val BASE_VIEW_TAG = "OnboardingView"
        const val SKIP_VIEW_TAG = "OnboardingSkipButton"
        const val NEXT_VIEW_TAG = "OnboardingNextButton"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingScreen(
    analyticsLibrary: AnalyticsLibraryInterface,
    model: OnboardingViewModel = OnboardingViewModel(
        analyticsLibrary,
        listOf()
    )
) {

    val viewModel = remember { model }

    Scaffold(
        modifier = Modifier.testTag(OnboardingTags.BASE_VIEW_TAG),
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

                }
            )
        }

    ) {  innerPadding ->

        val modifier = Modifier
            .padding(
                top = innerPadding.calculateTopPadding() + 12.dp,
                bottom = innerPadding.calculateBottomPadding() + 12.dp
            )

        val pagerState = rememberPagerState(pageCount = {
            viewModel.pages.size
        })

        HorizontalPager(modifier = modifier, state = pagerState) { position ->

            OnboardingView(
                page = viewModel.getPageDetails(position),
                onSkipPressed = viewModel.onSkipPressed(position),
                onNextPressed = viewModel.onNextPressed(position),
            )
        }
    }
}

@Composable
fun OnboardingView(
    page: OnboardingPage,
    onSkipPressed: () -> Unit,
    onNextPressed: () -> Unit,
) {

    Column {

        Text(
            text = page.viewTitle,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f),
            textAlign = TextAlign.Center,
        )

        Text(
            text = page.viewDescription,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f),
            textAlign = TextAlign.Center,
        )

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f),
            painter = painterResource(page.viewImage),
            contentDescription = "",
        )

        Row {

            Spacer(modifier = Modifier.width(12.dp))

            ElevatedButton(
                modifier = Modifier
                    .testTag(OnboardingTags.SKIP_VIEW_TAG)
                    .weight(0.1f),
                onClick = {

                    onSkipPressed()
                },
            ) {

                Text(text = page.viewSkipButton)
            }

            Spacer(modifier = Modifier.width(12.dp))

            ElevatedButton(
                modifier = Modifier
                    .testTag(OnboardingTags.NEXT_VIEW_TAG)
                    .weight(0.1f),
                onClick = {

                    onNextPressed()
                },
            ) {

                Text(text = page.viewNextButton)
            }

            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@ThemePreviews
@Composable
fun OnboardingPreview() {

    HelpDecideTheme {

        val analyticsLibrary = MockAnalyticsLibrary()
        OnboardingScreen(
            analyticsLibrary = MockAnalyticsLibrary(),
            model = OnboardingViewModel(
                analyticsLibrary,
                listOf(
                    OnboardingPage(
                        viewTitle = "Title 1",
                        viewDescription = "Description 1",
                        viewImage = R.drawable.text_icon,
                        viewNextButton = "Action 1",
                        viewNextPressed = {},
                        viewSkipButton = "Skip 1",
                        viewSkipPressed = {},
                    )
                )
            )
        )
    }
}