package com.chrisburrow.helpdecide.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.ui.viewmodels.AppStartupViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.HomeViewModel
import com.chrisburrow.helpdecide.ui.viewmodels.LoadingViewModel
import com.chrisburrow.helpdecide.ui.views.screens.HomeScreen
import com.chrisburrow.helpdecide.ui.views.screens.LoadingScreen
import com.chrisburrow.helpdecide.ui.views.screens.OnboardingScreen
import com.chrisburrow.helpdecide.utils.speechtotext.SpeechToTextToTextRequest
import kotlinx.coroutines.launch

enum class Screen {
    Loading,
    Onboarding,
    Home,
}

sealed class NavigationItem(val route: String) {
    object Loading : NavigationItem(Screen.Loading.name)
    object Onboarding : NavigationItem(Screen.Onboarding.name)
    object Home : NavigationItem(Screen.Home.name)
}

@Composable
fun AppNavHost (
    modifier: Modifier = Modifier,
    navController: NavHostController,
    analyticsLibrary: AnalyticsLibraryInterface,
    startDestination: String = NavigationItem.Onboarding.route,
) {

    val scope = rememberCoroutineScope()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    )
    {
        composable(NavigationItem.Loading.route) {

            LoadingScreen(AppStartupViewModel(navController, analyticsLibrary))
        }

        composable(NavigationItem.Onboarding.route) {

            OnboardingScreen(
                analyticsLibrary = analyticsLibrary,
                navigateToNextScreen = {

                    scope.launch {

                        analyticsLibrary.settingsShown()
                        navController.navigateAndPopUpTo(NavigationItem.Home.route)
                    }
                }
            )
        }

        composable(NavigationItem.Home.route) {

            val isSpeechCompatible = SpeechToTextToTextRequest(LocalContext.current).isSpeechCompatible()
            HomeScreen(analyticsLibrary, HomeViewModel(analyticsLibrary, isSpeechCompatible))
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