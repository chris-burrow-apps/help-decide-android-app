package com.chrisburrow.helpdecide.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.chrisburrow.helpdecide.R
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsScreens
import com.chrisburrow.helpdecide.ui.libraries.preferences.PreferencesLibraryInterface
import com.chrisburrow.helpdecide.ui.viewmodels.AddOptionViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.AppStartupViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.DecideWheelViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.DecisionDefaultViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.DecisionViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.GeneralDialogConfig
import com.chrisburrow.helpdecide.ui.viewmodels.GeneralDialogViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.HomeViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.SettingsViewModel
import com.chrisburrow.helpdecide.ui.views.dialogs.AddOptionDialog
import com.chrisburrow.helpdecide.ui.views.dialogs.DecideWheelDialog
import com.chrisburrow.helpdecide.ui.views.dialogs.DecisionDefaultDialog
import com.chrisburrow.helpdecide.ui.views.dialogs.DecisionDialog
import com.chrisburrow.helpdecide.ui.views.dialogs.GeneralDialog
import com.chrisburrow.helpdecide.ui.views.dialogs.SettingsDialog
import com.chrisburrow.helpdecide.ui.views.screens.HomeScreen
import com.chrisburrow.helpdecide.ui.views.screens.LoadingScreen
import com.chrisburrow.helpdecide.ui.views.screens.OnboardingScreen
import com.chrisburrow.helpdecide.utils.OptionObject
import com.chrisburrow.helpdecide.utils.speechtotext.SpeechToText
import kotlinx.coroutines.launch

enum class Screen {
    Loading,
    Onboarding,
    Home,
}

sealed class NavigationScreenItem(val route: String) {
    data object Loading : NavigationScreenItem(Screen.Loading.name)
    data object Onboarding : NavigationScreenItem(Screen.Onboarding.name)
    data object Home : NavigationScreenItem(Screen.Home.name)
}

enum class Dialog {
    AddOption,
    SpeechToText,
    DecideType,
    InstantDecision,
    SpinTheWheel,
    Settings,
    DeleteAll,
    AddAnother
}

sealed class NavigationDialogItem(val route: String) {
    data object AddOption : NavigationDialogItem(Dialog.AddOption.name)
    data object SpeechToText : NavigationDialogItem(Dialog.SpeechToText.name)
    data object DecideType : NavigationDialogItem(Dialog.DecideType.name)
    data object InstantDecision : NavigationDialogItem(Dialog.InstantDecision.name)
    data object SpinTheWheel : NavigationDialogItem(Dialog.SpinTheWheel.name)
    data object Settings : NavigationDialogItem(Dialog.Settings.name)
    data object DeleteAll : NavigationDialogItem(Dialog.DeleteAll.name)
    data object AddAnother : NavigationDialogItem(Dialog.AddAnother.name)
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

            HomeScreen(navController, homeViewModel)
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

        dialog(NavigationDialogItem.Settings.route) {

            SettingsDialog(viewModel = SettingsViewModel(analyticsLibrary)) {

                navController.popBackStack()
            }
        }

        dialog(NavigationDialogItem.DeleteAll.route) {

            GeneralDialog(
                viewModel = GeneralDialogViewModel(
                    configuration = GeneralDialogConfig(
                        screenName = AnalyticsScreens.RemoveAll,
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
                        screenName = AnalyticsScreens.RemoveAll,
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

        dialog(NavigationDialogItem.DecideType.route) {

            val spinTheWheelKey = "spinTheWheel"
            val instantKey = "instant"

            val options: LinkedHashMap<String, String> = linkedMapOf(
                spinTheWheelKey to stringResource(R.string.spin_the_wheel),
                instantKey to stringResource(R.string.instant_decision),
            )

            DecisionDefaultDialog(
                viewModel = DecisionDefaultViewModel(
                    analyticsLibrary = analyticsLibrary,
                    preferencesLibrary = preferencesLibrary,
                    options = options,
                ),
                selectedKey = { key ->

                    navController.popBackStack()

                    when (key) {

                        spinTheWheelKey -> {

                            navController.navigate(NavigationDialogItem.SpinTheWheel.route)
                        }

                        instantKey -> {

                            navController.navigate(NavigationDialogItem.InstantDecision.route)
                        }
                    }
                }
            )
        }

        dialog(NavigationDialogItem.SpinTheWheel.route) {

            val options = homeViewModel.view.collectAsState()

            DecideWheelDialog(
                DecideWheelViewModel(
                    analyticsLibrary = analyticsLibrary,
                    options = options.value.options
                ),
                removePressed = { option ->

                    homeViewModel.deleteOption(option)
                    navController.popBackStack()
                },
                dismissPressed = {

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