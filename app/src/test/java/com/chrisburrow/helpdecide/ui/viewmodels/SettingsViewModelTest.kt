package com.chrisburrow.helpdecide.ui.viewmodels

import android.os.SystemClock
import com.chrisburrow.helpdecide.MainDispatcherRule
import com.chrisburrow.helpdecide.ui.libraries.analytics.MockAnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.preferences.MockPreferencesLibrary
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test

class SettingsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun defaultState() {

        val analyticsLibrary = MockAnalyticsLibrary(analyticsState = false)
        val preferencesLibrary = MockPreferencesLibrary()

        val viewModel = SettingsViewModel(
            analyticsLibrary = analyticsLibrary,
            preferencesLibrary = preferencesLibrary,
            options = linkedMapOf(),
        )

        val state = viewModel.view.value

        assertFalse(state.googleAnalyticsEnabled)
        assertTrue(state.googleAnalyticsLoading)
        assertFalse(state.crashalyticsEnabled)
        assertTrue(state.crashalyticsLoading)
        assertFalse(state.alwaysAskEnabled)
        assertTrue(state.alwaysAskLoading)
        assertTrue(state.decisionTypeLoading)
        assertEquals("", state.decisionTypeString)
        assertEquals("", state.versionName)
    }

    @Test
    fun refreshPermissions() {

        val expectedCrashlytics = true
        val expectedAnalytics = true
        val expectedSkipDecisionType = true
        val expectedDecisionType = System.currentTimeMillis().toString()
        val expectedVersionName = System.currentTimeMillis().toString()

        val analyticsLibrary = MockAnalyticsLibrary(
            analyticsState = expectedAnalytics,
            crashayticsState = expectedCrashlytics
        )
        val preferencesLibrary = MockPreferencesLibrary(
            shouldSkipDecisionType = expectedSkipDecisionType,
            defaultDecisionOption = expectedDecisionType,
            versionCode = expectedVersionName
        )

        val viewModel = SettingsViewModel(
            analyticsLibrary = analyticsLibrary,
            preferencesLibrary = preferencesLibrary,
            options = linkedMapOf("" to expectedDecisionType),
        )

        viewModel.refreshPermissions()

        val state = viewModel.view.value

        assertFalse(state.googleAnalyticsLoading)
        assertFalse(state.crashalyticsLoading)
        assertFalse(state.alwaysAskLoading)
        assertFalse(state.decisionTypeLoading)
        assertTrue(state.googleAnalyticsEnabled)
        assertTrue(state.crashalyticsEnabled)
        assertTrue(state.alwaysAskEnabled)
        assertEquals(expectedDecisionType, state.decisionTypeString)
        assertEquals(expectedVersionName, state.versionName)
    }

    @Test
    fun toggleAnalytics() {

        val analyticsLibrary = MockAnalyticsLibrary(analyticsState = false)
        val preferencesLibrary = MockPreferencesLibrary()

        val viewModel = SettingsViewModel(
            analyticsLibrary = analyticsLibrary,
            preferencesLibrary = preferencesLibrary,
            options = linkedMapOf(),
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
            preferencesLibrary = preferencesLibrary,
            options = linkedMapOf(),
        )

        assertFalse(analyticsLibrary.setCrashalyticsStateCalled)

        viewModel.toggleCrashalytics(true)

        assertTrue(analyticsLibrary.setCrashalyticsStateCalled)
        assertTrue(analyticsLibrary.crashayticsState)
    }
}