package com.chrisburrow.helpdecide.ui.libraries.analytics

interface AnalyticsLibraryInterface {

    suspend fun getCrashalyticsState() : Boolean
    suspend fun getAnalyticsState() : Boolean

    suspend fun setCrashalyticsState(enabled: Boolean)
    suspend fun setAnalyticsState(enabled: Boolean)

    suspend fun checkSettingsShown(): Boolean
    suspend fun permissionsRequested()

    fun logScreenView(screenName: String)
    fun logButtonPressed(buttonText: String)
}