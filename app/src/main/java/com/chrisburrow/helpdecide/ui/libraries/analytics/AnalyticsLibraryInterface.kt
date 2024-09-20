package com.chrisburrow.helpdecide.ui.libraries.analytics

import kotlinx.coroutines.flow.Flow

interface AnalyticsLibraryInterface {

    suspend fun getCrashalyticsState() : Flow<Boolean>
    suspend fun getAnalyticsState() : Flow<Boolean>

    suspend fun setCrashalyticsState(enabled: Boolean)
    suspend fun setAnalyticsState(enabled: Boolean)

    suspend fun checkSettingsShown(): Flow<Boolean>
    suspend fun settingsShown()

    fun logScreenView(screenName: String)
    fun logButtonPressed(buttonText: String)
}