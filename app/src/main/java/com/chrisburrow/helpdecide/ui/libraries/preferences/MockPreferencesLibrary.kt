package com.chrisburrow.helpdecide.ui.libraries.preferences

class MockPreferencesLibrary(
    var onboardingShown: Boolean = true,
    var defaultDecisionOption: String = "",
    private var versionCode: String = ""
) : PreferencesLibraryInterface {

    var checkDefaultDecisionOptionCalled: Boolean = false
    var saveDefaultDecisionOptionCalledWithKey: String = ""

    override suspend fun checkPermissionsShown(): Boolean {

        return onboardingShown
    }

    override suspend fun permissionsRequested() {

        onboardingShown = true
    }

    override suspend fun checkDefaultDecisionOption(): String {

        checkDefaultDecisionOptionCalled = true

        return defaultDecisionOption
    }

    override suspend fun saveDefaultDecisionOption(key: String) {

        saveDefaultDecisionOptionCalledWithKey = key

        defaultDecisionOption = key
    }

    override fun checkVersionName(): String {

        return versionCode
    }
}