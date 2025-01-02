package com.chrisburrow.helpdecide.ui.viewmodels

import com.chrisburrow.helpdecide.MainDispatcherRule
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsActions
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.preferences.MockPreferencesLibrary
import com.chrisburrow.helpdecide.utils.OptionObject
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class SettingsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun toggleAnalytics() {

        val analyticsLibrary = MockAnalyticsLibrary(analyticsState = false)
        val preferencesLibrary = MockPreferencesLibrary()

        val viewModel = SettingsViewModel(
            analyticsLibrary = analyticsLibrary,
            preferencesLibrary = preferencesLibrary
        )

        assertFalse(analyticsLibrary.setAnalyticsStateCalled)

        viewModel.toggleGoogleAnalytics(true)

        assertTrue(analyticsLibrary.setAnalyticsStateCalled)
        assertTrue(analyticsLibrary.analyticsState)
    }

    @Test
    fun toggleCrashReporting() {

        val analyticsLibrary = MockAnalyticsLibrary(crashayticsState = false)
        val preferencesLibrary = MockPreferencesLibrary()

        val viewModel = SettingsViewModel(
            analyticsLibrary = analyticsLibrary,
            preferencesLibrary = preferencesLibrary
        )

        assertFalse(analyticsLibrary.setCrashalyticsStateCalled)

        viewModel.toggleCrashalytics(true)

        assertTrue(analyticsLibrary.setCrashalyticsStateCalled)
        assertTrue(analyticsLibrary.crashayticsState)
    }

    @Test
    fun toggleAlwaysAsk() {

        val analyticsLibrary = MockAnalyticsLibrary()
        val preferencesLibrary = MockPreferencesLibrary(alwaysAskOption = false)

        val viewModel = SettingsViewModel(
            analyticsLibrary = analyticsLibrary,
            preferencesLibrary = preferencesLibrary
        )

        viewModel.toggleAlwaysAsk(true)

        assertTrue(preferencesLibrary.alwaysAskOptionCalledWith!!)
        assertTrue(preferencesLibrary.alwaysAskOption)
    }
}