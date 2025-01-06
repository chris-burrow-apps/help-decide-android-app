package com.chrisburrow.helpdecide.ui.libraries.preferences

class MockPreferencesLibrary(
    var onboardingShown: Boolean = true,
    var defaultDecisionOption: String = "",
    var shouldSkipDecisionType: Boolean = false,
    private var versionCode: String = ""
) : PreferencesLibraryInterface {

    var checkDefaultDecisionOptionCalled: Boolean = false
    var saveDefaultDecisionOptionCalledWithKey: String? = null
    var shouldSkipDecisionTypeCalled: Boolean = false
    var shouldSkipDecisionTypeCalledWith: Boolean? = null

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

    override suspend fun shouldSkipDecisionType(): Boolean {

        shouldSkipDecisionTypeCalled = true

        return shouldSkipDecisionType
    }

    override suspend fun saveSkipDecisionType(enabled: Boolean) {

        shouldSkipDecisionTypeCalledWith = enabled

        shouldSkipDecisionType = enabled
    }

    override fun checkVersionName(): String {

        return versionCode
    }
}