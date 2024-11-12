package com.chrisburrow.helpdecide

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.HelpDecideApp
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.robots.home
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class VoiceJourneyTest {

    @get:Rule
    val rule = createComposeRule()

    fun setup(voiceCompatible: Boolean) {

        rule.setContent {

            HelpDecideTheme {

                HelpDecideApp(
                    analyticsLibrary = MockAnalyticsLibrary(settingsShown = true),
                    voiceCompatible = voiceCompatible
                )
            }
        }
    }

    @Test
    fun checkVoiceButtonShown() = runTest {

        setup(voiceCompatible = true)

        home(rule) {

            checkAddByVoiceShown()
        }
    }

    @Test
    fun checkVoiceButtonHidden() = runTest {

        setup(voiceCompatible = false)

        home(rule) {

            checkAddByVoiceHidden()
        }
    }
}