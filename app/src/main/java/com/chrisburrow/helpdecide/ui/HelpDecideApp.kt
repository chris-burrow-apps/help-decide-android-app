package com.chrisburrow.helpdecide.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface

@Composable
fun HelpDecideApp(
    navController: NavHostController = rememberNavController(),
    analyticsLibrary: AnalyticsLibraryInterface,
){

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {

        AppNavHost(
            navController = navController,
            analyticsLibrary = analyticsLibrary,
            startDestination = NavigationScreenItem.Loading.route
        )
    }
}