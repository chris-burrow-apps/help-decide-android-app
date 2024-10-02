package com.chrisburrow.helpdecide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.chrisburrow.helpdecide.ui.AppNavHost
import com.chrisburrow.helpdecide.ui.NavigationItem
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.storage.StorageLibrary
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme


class DecideActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        setContent {

            val storageLibrary = StorageLibrary(LocalContext.current)
            val analyticsLibrary = AnalyticsLibrary(LocalContext.current, storageLibrary = storageLibrary)

            HelpDecideTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {

                    AppNavHost(
                        navController = rememberNavController(),
                        analyticsLibrary = analyticsLibrary,
                        startDestination = NavigationItem.Loading.route
                    )
                }
            }
        }
    }
}

