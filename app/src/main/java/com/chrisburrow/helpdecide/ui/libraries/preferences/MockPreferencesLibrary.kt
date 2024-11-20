package com.chrisburrow.helpdecide.ui.libraries.preferences

class MockPreferencesLibrary(
    var onboardingShown: Boolean = true,
) : PreferencesLibraryInterface {


    override suspend fun checkPermissionsShown(): Boolean {

        return onboardingShown
    }

    override suspend fun permissionsRequested() {

        onboardingShown = true
    }
}