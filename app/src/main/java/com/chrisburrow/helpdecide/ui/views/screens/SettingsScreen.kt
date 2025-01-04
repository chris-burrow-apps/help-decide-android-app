package com.chrisburrow.helpdecide.ui.views.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.ThemePreviews
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.preferences.DecisionTypeLookup
import com.chrisburrow.helpdecide.ui.libraries.preferences.MockPreferencesLibrary
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.SettingsViewModel
import com.chrisburrow.helpdecide.ui.views.screens.settings.SettingsList
import com.chrisburrow.helpdecide.utils.SettingsBooleanRow
import com.chrisburrow.helpdecide.utils.SettingsStringRow

class SettingsScreenTags {

    companion object {

        const val BASE_VIEW_TAG = "SettingsScreen"
        const val TITLE_VIEW_TAG = "SettingsTitle"
        const val BACK_BUTTON_TAG = "BackButton"
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewmodel: SettingsViewModel,
    decisionTypePressed: () -> Unit,
    onBackPressed: () -> Unit,
) {
    val viewModel = remember { viewmodel }
    val view by viewModel.view.collectAsState()

    Scaffold(
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
                        stringResource(R.string.settings),
                        fontSize = 35.sp,
                        fontFamily = FontFamily.Cursive,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon =
                {
                    IconButton(
                        modifier = Modifier.testTag(SettingsScreenTags.BACK_BUTTON_TAG),
                        onClick = {
                        onBackPressed()
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

        Surface(
            modifier = Modifier
                .testTag(SettingsScreenTags.BASE_VIEW_TAG)
                .padding(top = innerPadding.calculateTopPadding()),
            shape = RoundedCornerShape(8.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                SettingsList(options = listOf(

                    SettingsBooleanRow(
                        title = stringResource(R.string.analytics),
                        description = stringResource(R.string.analytics_desc),
                        switchPosition = view.googleAnalyticsEnabled,
                        loading = view.googleAnalyticsLoading
                    ) { toggled ->

                        viewModel.toggleGoogleAnalytics(toggled)
                    },
                    SettingsBooleanRow(
                        title = stringResource(R.string.crashalytics),
                        description = stringResource(R.string.crashalytics_desc),
                        switchPosition = view.crashalyticsEnabled,
                        loading = view.crashalyticsLoading
                    ) { toggled ->

                        viewModel.toggleCrashalytics(toggled)
                    },
                    SettingsBooleanRow(
                        title = stringResource(R.string.always_ask_decision),
                        description = stringResource(R.string.always_ask_decision_desc),
                        switchPosition = view.alwaysAskEnabled,
                        loading = view.alwaysAskLoading
                    ) { toggled ->

                        viewModel.toggleAlwaysAsk(toggled)
                    },
                    SettingsStringRow(
                        title = stringResource(R.string.decision_type),
                        description = view.decisionType,
                    ) {

                        decisionTypePressed()
                    },
                    SettingsStringRow(
                        title = stringResource(R.string.version_name),
                        description = view.versionName,
                    )
                ))
            }
        }

        LaunchedEffect(Unit) {

            viewModel.logScreenView(AnalyticsScreens.SETTINGS)

            viewModel.refreshAnalytics()
        }
    }
}

@ThemePreviews
@Composable
fun SettingsDialogPreview() {

    HelpDecideTheme {

        SettingsScreen(
            SettingsViewModel(
                analyticsLibrary = MockAnalyticsLibrary(),
                preferencesLibrary = MockPreferencesLibrary(),
                decisionTypeLookup = DecisionTypeLookup(LocalContext.current)
            ),
            decisionTypePressed = {},
            onBackPressed = {},
        )
    }
}