package com.chrisburrow.helpdecide.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chrisburrow.helpdecide.ui.libraries.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.ui.robots.settings
import com.chrisburrow.helpdecide.ui.theme.HelpDecideTheme
import com.chrisburrow.helpdecide.ui.viewmodels.SettingsViewModel
import com.chrisburrow.helpdecide.ui.views.dialogs.SettingsDialog
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsDialogTest {

    @get:Rule
    val rule = createComposeRule()

    val mockAnalyticsLibrary = MockAnalyticsLibrary(
        crashalyticsState = false,
        analyticsState = false
    )

    @Test
    fun optionsShown() {

        rule.setContent {

            HelpDecideTheme {

                SettingsDialog(model = SettingsViewModel(mockAnalyticsLibrary)) { }
            }
        }

        settings(rule) {

            checkText(0, "Analytics", "Allowing me to see what parts of the app is used so I can improve it in future with extra features.")
            checkText(1, "Crashalytics", "If the app crashes, a crash report will be auto sent to me so I can diagnose what happened.")
        }
    }

    @Test
    fun optionsToggled() = runTest {

        rule.setContent {

            HelpDecideTheme {

                SettingsDialog { }
            }
        }

        settings(rule) {

            assertTrue(mockAnalyticsLibrary.getAnalyticsStateCalled)
            assertTrue(mockAnalyticsLibrary.getCrashalyticsStateCalled)
        }
    }

    class MockAnalyticsLibrary(val crashalyticsState: Boolean, val analyticsState: Boolean) : AnalyticsLibraryInterface {

        var getAnalyticsStateCalled = false
        var getCrashalyticsStateCalled = false

        var setAnalyticsStateCalled = false
        var setAnalyticsStateCalledWith: Boolean? = null

        var setCrashalyticsStateCalled = false
        var setCrashalyticsStateCalledWith: Boolean? = null

        override suspend fun getCrashalyticsState(): Flow<Boolean> {

            getCrashalyticsStateCalled = true

            return flow { emit(crashalyticsState) }
        }

        override suspend fun getAnalyticsState(): Flow<Boolean> {

            getAnalyticsStateCalled = true

            return flow { emit(analyticsState) }
        }

        override suspend fun setCrashalyticsState(enabled: Boolean) {

            setCrashalyticsStateCalled = true

            setCrashalyticsStateCalledWith = enabled
        }

        override suspend fun setAnalyticsState(enabled: Boolean) {

            setAnalyticsStateCalled = true

            setAnalyticsStateCalledWith = enabled
        }
    }
}