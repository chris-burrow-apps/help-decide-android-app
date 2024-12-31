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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class OnboardingTags {

    companion object {

        const val BASE_VIEW_TAG = "OnboardingView"
        const val TITLE_VIEW_TAG = "OnboardingTitleView"
        const val DESCRIPTION_VIEW_TAG = "OnboardingDescView"
        const val SKIP_VIEW_TAG = "OnboardingSkipButton"
        const val NEXT_VIEW_TAG = "OnboardingNextButton"
    }
}

data class OnboardingPage(
    var viewTitle: String,
    var viewDescription: String,
    var viewImage: Int,
    var viewNextButton: String,
    var viewNextPressed: () -> Unit,
    var viewSkipButton: String = "",
    var viewSkipPressed: () -> Unit = {}
)

@Composable
fun OnboardingScreen(
    analyticsLibrary: AnalyticsLibraryInterface,
    navigateToNextScreen: () -> Unit
) {

    Scaffold(
        modifier = Modifier.testTag(OnboardingTags.BASE_VIEW_TAG),
    ) {  innerPadding ->

        val modifier = Modifier
            .padding(
                top = innerPadding.calculateTopPadding() + 12.dp,
                bottom = innerPadding.calculateBottomPadding() + 12.dp
            )

        val pagerState = rememberPagerState(pageCount = { 3 })

        val scope = rememberCoroutineScope()

        val onboardingScreens = listOf(

            OnboardingPage(
                viewTitle = stringResource(R.string.onboarding_welcome_title),
                viewDescription = stringResource(R.string.onboarding_welcome_desc),
                viewImage = R.drawable.mock_spin_the_wheel,
                viewNextButton = stringResource(R.string.next),
                viewNextPressed = {

                    animateToPage(scope, pagerState, 1)
                }
            ),
            OnboardingPage(
                viewTitle = stringResource(id = R.string.crashalytics),
                viewDescription = stringResource(id = R.string.crashalytics_desc),
                viewImage = R.drawable.crash_reporting,
                viewNextButton = stringResource(R.string.enable),
                viewNextPressed = {

                    scope.launch(Dispatchers.IO) {

                        analyticsLibrary.setCrashalyticsState(true)
                    }

                    animateToPage(scope, pagerState, 2)
                },
                viewSkipButton = stringResource(R.string.disable),
                viewSkipPressed = {

                    scope.launch(Dispatchers.IO) {

                        analyticsLibrary.setCrashalyticsState(false)
                    }

                    animateToPage(scope, pagerState, 2)
                }
            ),
            OnboardingPage(
                viewTitle = stringResource(id = R.string.analytics),
                viewDescription = stringResource(id = R.string.analytics_desc),
                viewImage = R.drawable.analytics,
                viewNextButton = stringResource(R.string.enable),
                viewNextPressed = {

                    scope.launch(Dispatchers.IO) {

                        analyticsLibrary.setAnalyticsState(true)
                    }

                    navigateToNextScreen()
                },
                viewSkipButton = stringResource(R.string.disable),
                viewSkipPressed = {

                    scope.launch(Dispatchers.IO) {

                        analyticsLibrary.setAnalyticsState(false)
                    }

                    navigateToNextScreen()
                }
            ),
        )

        HorizontalPager(modifier = modifier, state = pagerState, userScrollEnabled = false) { position ->

            val page = onboardingScreens[position]

            OnboardingView(position, page)
        }
    }
}

@Composable
fun OnboardingView(
    position: Int,
    page: OnboardingPage,
) {

    Column(
        modifier = Modifier.padding(all = 16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.7f),
            painter = painterResource(page.viewImage),
            contentDescription = "",
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.TopStart
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f)
                .testTag(OnboardingTags.TITLE_VIEW_TAG + position),
            text = page.viewTitle,
            fontSize = 35.sp,
            fontFamily = FontFamily.Cursive,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )

        Text(
            text = page.viewDescription,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(OnboardingTags.DESCRIPTION_VIEW_TAG + position),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row {

            if(page.viewSkipButton.isNotEmpty()) {

                ElevatedButton(
                    modifier = Modifier
                        .testTag(OnboardingTags.SKIP_VIEW_TAG + position)
                        .weight(0.1f),
                    onClick = {

                        page.viewSkipPressed()
                    },
                ) {

                    Text(text = page.viewSkipButton)
                }

                Spacer(modifier = Modifier.width(12.dp))
            }

            ElevatedButton(
                modifier = Modifier
                    .testTag(OnboardingTags.NEXT_VIEW_TAG + position)
                    .weight(0.1f),
                onClick = {

                    page.viewNextPressed()
                },
            ) {

                Text(text = page.viewNextButton)
            }
        }
    }
}

fun animateToPage(scope: CoroutineScope, pagerState: PagerState, pageNumber: Int) {

    scope.launch {

        pagerState.animateScrollToPage(
            page = pageNumber
        )
    }
}

@ThemePreviews
@Composable
fun OnboardingPreview() {

    HelpDecideTheme {

        OnboardingScreen(
            analyticsLibrary = MockAnalyticsLibrary(),
            navigateToNextScreen = {}
        )
    }
}