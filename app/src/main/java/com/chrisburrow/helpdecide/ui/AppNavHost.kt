package com.chrisburrow.helpdecide.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.navArgument
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.preferences.DecisionTypeLookup
import com.chrisburrow.helpdecide.ui.libraries.preferences.PreferencesLibraryInterface
import com.chrisburrow.helpdecide.ui.viewmodels.AddOptionViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.AppStartupViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.DecideWheelViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.DecisionDefaultViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.DecisionViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.GeneralDialogConfig
import com.chrisburrow.helpdecide.ui.viewmodels.GeneralDialogViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.HomeViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.PickABubbleViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.SettingsViewModel
import com.chrisburrow.helpdecide.ui.views.dialogs.AddOptionDialog
import com.chrisburrow.helpdecide.ui.views.screens.DecideWheelScreen
import com.chrisburrow.helpdecide.ui.views.dialogs.DecisionDefaultDialog
import com.chrisburrow.helpdecide.ui.views.dialogs.DecisionDialog
import com.chrisburrow.helpdecide.ui.views.dialogs.GeneralDialog
import com.chrisburrow.helpdecide.ui.views.screens.DecisionFlowScreen
import com.chrisburrow.helpdecide.ui.views.screens.PickABubbleScreen
import com.chrisburrow.helpdecide.ui.views.screens.SettingsScreen
import com.chrisburrow.helpdecide.ui.views.screens.HomeScreen
import com.chrisburrow.helpdecide.ui.views.screens.LoadingScreen
import com.chrisburrow.helpdecide.ui.views.screens.OnboardingScreen
import com.chrisburrow.helpdecide.utils.OptionObject
import com.chrisburrow.helpdecide.utils.speechtotext.SpeechToText
import kotlinx.coroutines.launch

enum class Screen {
    Loading,
    Settings,
    Onboarding,
    Home,
    SpinTheWheel,
    PickABubbleScreen,
    DecisionFlow,
}

sealed class NavigationScreenItem(val route: String) {
    data object Loading : NavigationScreenItem(Screen.Loading.name)
    data object Settings : NavigationDialogItem(Screen.Settings.name)
    data object Onboarding : NavigationScreenItem(Screen.Onboarding.name)
    data object Home : NavigationScreenItem(Screen.Home.name)
    data object DecisionFlow : NavigationScreenItem("${Screen.DecisionFlow.name}/{forceSkipDecisionTypeDialog}")
    data object PickABubbleScreen : NavigationScreenItem(Screen.PickABubbleScreen.name)
    data object SpinTheWheel : NavigationDialogItem(Screen.SpinTheWheel.name)
}

enum class Dialog {
    AddOption,
    SpeechToText,
    DecideType,
    SettingsDecideType,
    InstantDecision,
    DeleteAll,
    AddAnother,
    OptionChosen,
}

sealed class NavigationDialogItem(val route: String) {
    data object AddOption : NavigationDialogItem(Dialog.AddOption.name)
    data object SpeechToText : NavigationDialogItem(Dialog.SpeechToText.name)
    data object DecideType : NavigationDialogItem(Dialog.DecideType.name)
    data object SettingsDecideType : NavigationDialogItem(Dialog.SettingsDecideType.name)
    data object InstantDecision : NavigationDialogItem(Dialog.InstantDecision.name)
    data object DeleteAll : NavigationDialogItem(Dialog.DeleteAll.name)
    data object AddAnother : NavigationDialogItem(Dialog.AddAnother.name)
    data object OptionChosen : NavigationDialogItem("${Dialog.OptionChosen.name}/{optionId}")
}

@Composable
fun AppNavHost (
    modifier: Modifier = Modifier,
    navController: NavHostController,
    analyticsLibrary: AnalyticsLibraryInterface,
    preferencesLibrary: PreferencesLibraryInterface,
    voiceCompatible: Boolean,
    startDestination: String = NavigationScreenItem.Onboarding.route,
) {

    val scope = rememberCoroutineScope()

    val homeViewModel = remember {
        HomeViewModel(analyticsLibrary, voiceCompatible)
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    )
    {
        composable(NavigationScreenItem.Loading.route) {

            LoadingScreen(AppStartupViewModel(navController, preferencesLibrary))
        }

        composable(NavigationScreenItem.Onboarding.route) {

            OnboardingScreen(
                analyticsLibrary = analyticsLibrary,
                navigateToNextScreen = {

                    scope.launch {

                        preferencesLibrary.permissionsRequested()
                        navController.navigateAndPopUpTo(NavigationScreenItem.Home.route)
                    }
                }
            )
        }

        composable(NavigationScreenItem.Home.route) {

            HomeScreen(
                model = homeViewModel,
                decidePressed = {
                    navController.navigate("${NavigationScreenItem.DecisionFlow}/${false}")
                },
                addOptionPressed = {
                    navController.navigate(NavigationDialogItem.AddOption.route)
                },
                addVoicePressed = {
                    navController.navigate(NavigationDialogItem.SpeechToText.route)
                },
                deleteAllPressed = {
                    navController.navigate(NavigationDialogItem.DeleteAll.route)
                },
                settingsPressed = {
                    navController.navigate(NavigationScreenItem.Settings.route)
                },
                addAnotherHintShown = {
                    navController.navigate(NavigationDialogItem.AddAnother.route)
                },
            )
        }

        dialog(NavigationDialogItem.AddOption.route) {

            AddOptionDialog(
                viewModel = AddOptionViewModel(analyticsLibrary),
                optionSaved = {

                    homeViewModel.addOption(OptionObject(text = it))
                    navController.popBackStack()
                },
                optionCancelled = {

                    navController.popBackStack()
                }
            )
        }

        composable(NavigationScreenItem.Settings.route) {

            SettingsScreen(
                viewmodel = SettingsViewModel(
                    analyticsLibrary = analyticsLibrary,
                    preferencesLibrary = preferencesLibrary,
                    options = DecisionTypeLookup.options(LocalContext.current)
                ),
                decisionTypePressed = {

                    navController.navigate(NavigationDialogItem.SettingsDecideType.route)
                },
                onBackPressed = {

                    navController.popBackStack()
                }
            )
        }

        dialog(NavigationDialogItem.DeleteAll.route) {

            GeneralDialog(
                viewModel = GeneralDialogViewModel(
                    configuration = GeneralDialogConfig(
                        screenName = AnalyticsScreens.REMOVE_ALL,
                        description = stringResource(id = R.string.confirm_delete_desc),
                        confirmText = stringResource(id = R.string.delete_all_button),
                        confirmPressed = {
                            homeViewModel.clearOptions()
                            navController.popBackStack()
                        },
                        cancelText = stringResource(id = R.string.cancel),
                        cancelPressed = {
                            navController.popBackStack()
                        },
                    ),
                    analyticsLibrary = analyticsLibrary,
                )
            )
        }

        dialog(NavigationDialogItem.AddAnother.route) {

            GeneralDialog(
                viewModel = GeneralDialogViewModel(
                    configuration = GeneralDialogConfig(
                        screenName = AnalyticsScreens.ADD_ANOTHER,
                        description = stringResource(id = R.string.add_another_desc),
                        confirmText = stringResource(id = R.string.continue_option),
                        confirmPressed = {

                            navController.popBackStack()
                            navController.navigate(NavigationDialogItem.InstantDecision.route)
                        },
                        cancelText = stringResource(id = R.string.cancel),
                        cancelPressed = {

                            navController.popBackStack()
                        },
                    ),
                    analyticsLibrary = analyticsLibrary,
                )
            )
        }

        composable(
            route = NavigationScreenItem.DecisionFlow.route,
            arguments = listOf(navArgument("forceSkipDecisionTypeDialog") { type = NavType.BoolType })
        ) { navArgument ->

            val forceSkipDecisionTypeDialog = navArgument.arguments?.getBoolean("forceSkipDecisionTypeDialog") ?: false

            DecisionFlowScreen(
                preferencesLibrary = preferencesLibrary,
                forceSkipDecisionTypeDialog = forceSkipDecisionTypeDialog,
                showDecisionType = {
                    navController.popBackStack()
                    navController.navigate(NavigationDialogItem.DecideType.route)
                },
                showInstantDecision = {
                    navController.popBackStack()
                    navController.navigate(NavigationDialogItem.InstantDecision.route)
                },
                showPopTheBubble = {
                    navController.popBackStack()
                    navController.navigate(NavigationScreenItem.PickABubbleScreen.route)
                },
                showSpinTheWheel = {
                    navController.popBackStack()
                    navController.navigate(NavigationScreenItem.SpinTheWheel.route)
                }
            )
        }

        dialog(NavigationDialogItem.DecideType.route) {

            DecisionDefaultDialog(
                viewModel = DecisionDefaultViewModel(
                    analyticsLibrary = analyticsLibrary,
                    preferencesLibrary = preferencesLibrary,
                    options = DecisionTypeLookup.options(LocalContext.current),
                    doneButtonText = stringResource(R.string.go),
                    pressedDone = {

                        navController.popBackStack()
//                        navController.navigate(NavigationScreenItem.DecisionFlow.route)
                        navController.navigate("${NavigationScreenItem.DecisionFlow}/${true}")
                    }
                ),
            )
        }

        dialog(NavigationDialogItem.SettingsDecideType.route) {

            DecisionDefaultDialog(
                viewModel = DecisionDefaultViewModel(
                    analyticsLibrary = analyticsLibrary,
                    preferencesLibrary = preferencesLibrary,
                    options = DecisionTypeLookup.options(LocalContext.current),
                    doneButtonText = stringResource(R.string.save),
                    pressedDone = { _ ->

                        // Force refresh by navigating to Home & back to Settings
                        navController.navigateAndPopUpTo(NavigationScreenItem.Home.route)
                        navController.navigate(NavigationScreenItem.Settings.route)
                    }
                ),
            )
        }

        composable(NavigationScreenItem.SpinTheWheel.route) {

            val options = homeViewModel.view.collectAsState()

            DecideWheelScreen(
                DecideWheelViewModel(
                    analyticsLibrary = analyticsLibrary,
                    options = options.value.options
                ),
                removePressed = { optionId ->

                    homeViewModel.deleteOption(optionId)
                    navController.popBackStack()
                },
                backPressed = {

                    navController.popBackStack()
                },
                donePressed = {

                    navController.popBackStack()
                }
            )
        }

        dialog(NavigationDialogItem.InstantDecision.route) {

            val options = homeViewModel.view.collectAsState()

            DecisionDialog(
                DecisionViewModel(
                    analyticsLibrary = analyticsLibrary,
                    options = options.value.options
                ),
                removePressed = { option ->

                    homeViewModel.deleteOption(option)
                    navController.popBackStack()
                },
                donePressed = {

                    navController.popBackStack()
                }
            )
        }

        dialog(NavigationDialogItem.SpeechToText.route) {

            SpeechToText(response = {

                homeViewModel.addOption(OptionObject(text = it))
                navController.popBackStack()

            }, cancelled = {

                navController.popBackStack()
            })
        }

        composable(NavigationScreenItem.PickABubbleScreen.route) {

            val state = homeViewModel.view.collectAsState()

            PickABubbleScreen(
                model = PickABubbleViewModel(
                    analyticsLibrary = analyticsLibrary,
                    options = state.value.options
                ),
                optionPressed = {

                    navController.navigate("${NavigationDialogItem.OptionChosen}/$it")
                },
                backPressed = {

                    navController.popBackStack()
                }
            )
        }

        dialog(
            route = NavigationDialogItem.OptionChosen.route,
            arguments = listOf(navArgument("optionId") { type = NavType.StringType })
        ) { navArguments ->

            val optionId = navArguments.arguments?.getString("optionId")!!
            val state = homeViewModel.view.collectAsState()
            val optionText = state.value.options.first { it.id == optionId }.text

            GeneralDialog(
                viewModel = GeneralDialogViewModel(
                    configuration = GeneralDialogConfig(
                        screenName = AnalyticsScreens.DECISION_CHOSEN,
                        description = optionText,
                        confirmText = stringResource(id = R.string.done),
                        confirmPressed = {

                            navController.navigateAndPopUpTo(NavigationScreenItem.Home.route)
                        },
                        cancelText = stringResource(id = R.string.remove_option),
                        cancelPressed = {

                            homeViewModel.deleteOption(optionId)
                            navController.navigateAndPopUpTo(NavigationScreenItem.Home.route)
                        },
                    ),
                    analyticsLibrary = analyticsLibrary,
                )
            )
        }
    }
}

fun NavController.navigateAndPopUpTo(route: String) {

    val navController = this

    navController.navigate(route) {

        popUpTo(navController.graph.id) {

            inclusive = true
        }
    }
}