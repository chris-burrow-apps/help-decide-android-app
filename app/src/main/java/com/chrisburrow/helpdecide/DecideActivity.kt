package com.chrisburrow.helpdecide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import com.chrisburrow.helpdecide.ui.HelpDecideApp
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.preferences.PreferencesLibrary
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.utils.speechtotext.SpeechToTextToTextRequest

class DecideActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val packageInfo = packageManager.getPackageInfo(LocalContext.current.packageName, 0);

            HelpDecideTheme {
                // A surface container using the 'background' color from the theme
                HelpDecideApp(
                    analyticsLibrary = AnalyticsLibrary(
                        context = this.applicationContext,
                        storageLibrary = DecideApplication.storageLibrary
                    ),
                    preferencesLibrary = PreferencesLibrary(
                        storageLibrary = DecideApplication.storageLibrary,
                        versionName = "${packageInfo.versionName} (${packageInfo.longVersionCode})"
                    ),
                    voiceCompatible = SpeechToTextToTextRequest(LocalContext.current).isSpeechCompatible()
                )
            }
        }
    }
}

