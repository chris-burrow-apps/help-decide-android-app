package com.chrisburrow.helpdecide.ui.libraries.analytics

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockAnalyticsLibrary(
    var settingsShown: Boolean = true,
    var analyticsState: Boolean = false,
    var crashayticsState: Boolean = false,
) : AnalyticsLibraryInterface {

    lateinit var analyticsScreenName: String
    var analyticsButtonsPressed = mutableListOf<String>()

    var getAnalyticsStateCalled: Boolean = false
    var setAnalyticsStateCalled: Boolean = false

    var getCrashalyticsStateCalled: Boolean = false
    var setCrashalyticsStateCalled: Boolean = false

    override suspend fun getCrashalyticsState(): Boolean {

        getCrashalyticsStateCalled = true

        return crashayticsState
    }

    override suspend fun getAnalyticsState(): Boolean {

        getAnalyticsStateCalled = true

        return analyticsState
    }

    override suspend fun setCrashalyticsState(enabled: Boolean) {

        setCrashalyticsStateCalled = true

        crashayticsState = enabled
    }

    override suspend fun setAnalyticsState(enabled: Boolean) {

        setAnalyticsStateCalled = true

        analyticsState = enabled
    }

    override fun logScreenView(screenName: String) {

        analyticsScreenName = screenName
    }

    override fun logButtonPressed(buttonText: String) {

        analyticsButtonsPressed.add(buttonText)
    }

    override suspend fun checkSettingsShown(): Boolean {

        return settingsShown
    }

    override suspend fun permissionsRequested() {

        settingsShown = true
    }

    fun logScreenCalledWith(screenName: String) : Boolean {

        return screenName == analyticsScreenName
    }

    fun logButtonCalledWith(buttonText: String): Boolean {

        return analyticsButtonsPressed.contains(buttonText)
    }
}