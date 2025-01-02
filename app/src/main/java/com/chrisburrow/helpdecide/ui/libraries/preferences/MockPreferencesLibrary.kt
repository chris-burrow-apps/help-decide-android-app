package com.chrisburrow.helpdecide.ui.libraries.preferences

class MockPreferencesLibrary(
    var onboardingShown: Boolean = true,
    var defaultDecisionOption: String = "",
    var alwaysAskOption: Boolean = true,
    private var versionCode: String = ""
) : PreferencesLibraryInterface {

    var checkDefaultDecisionOptionCalled: Boolean = false
    var saveDefaultDecisionOptionCalledWithKey: String? = null
    var alwaysAskOptionCalled: Boolean = false
    var alwaysAskOptionCalledWith: Boolean? = null

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

    override suspend fun alwaysAskDecisionDialog(): Boolean {

        alwaysAskOptionCalled = true

        return alwaysAskOption
    }

    override suspend fun alwaysAskDecisionOption(enabled: Boolean) {

        alwaysAskOptionCalledWith = enabled

        alwaysAskOption = enabled
    }

    override fun checkVersionName(): String {

        return versionCode
    }
}