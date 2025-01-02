package com.chrisburrow.helpdecide.ui.libraries.preferences

interface PreferencesLibraryInterface {

    suspend fun checkPermissionsShown(): Boolean
    suspend fun permissionsRequested()

    suspend fun checkDefaultDecisionOption(): String
    suspend fun saveDefaultDecisionOption(key: String)

    suspend fun alwaysAskDecisionDialog() : Boolean
    suspend fun alwaysAskDecisionOption(enabled: Boolean)

    fun checkVersionName(): String
}