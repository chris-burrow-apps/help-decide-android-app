package com.chrisburrow.helpdecide.ui.libraries.analytics

interface AnalyticsLibraryInterface {

    suspend fun getCrashalyticsState() : Boolean
    suspend fun getAnalyticsState() : Boolean

    suspend fun setCrashalyticsState(enabled: Boolean)
    suspend fun setAnalyticsState(enabled: Boolean)

    fun logScreenView(screenName: String)
    fun logButtonPressed(buttonText: String)
}