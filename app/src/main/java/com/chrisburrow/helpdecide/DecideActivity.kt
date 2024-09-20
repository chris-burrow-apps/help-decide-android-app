package com.chrisburrow.helpdecide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.storage.StorageLibrary
import com.chrisburrow.helpdecide.ui.views.screens.HomeScreen
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.HomeViewModel
import com.chrisburrow.helpdecide.utils.speechtotext.SpeechToTextToTextRequest


class DecideActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val storageLibrary = StorageLibrary(LocalContext.current)
            val analyticsLibrary = AnalyticsLibrary(LocalContext.current, storageLibrary = storageLibrary)

            val isSpeechCompatible = SpeechToTextToTextRequest(LocalContext.current).isSpeechCompatible()

            HelpDecideTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surface
                ) {

                    HomeScreen(analyticsLibrary, HomeViewModel(analyticsLibrary, isSpeechCompatible))
                }
            }
        }
    }
}

