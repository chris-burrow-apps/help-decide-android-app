package com.chrisburrow.helpdecide.ui.libraries.preferences

interface PreferencesLibraryInterface {

    suspend fun checkPermissionsShown(): Boolean
    suspend fun permissionsRequested()
}