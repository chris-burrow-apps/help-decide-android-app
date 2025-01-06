package com.chrisburrow.helpdecide.ui.libraries.preferences

import com.chrisburrow.helpdecide.ui.libraries.storage.StorageLibraryInterface
import com.chrisburrow.helpdecide.ui.libraries.storage.StorageLibraryKeys
import kotlinx.coroutines.flow.first

interface PreferencesLibraryInterface {

    suspend fun checkPermissionsShown(): Boolean
    suspend fun permissionsRequested()

    suspend fun checkDefaultDecisionOption(): String
    suspend fun saveDefaultDecisionOption(key: String)

    suspend fun shouldSkipDecisionType() : Boolean
    suspend fun saveSkipDecisionType(enabled: Boolean)

    fun checkVersionName(): String
}

class PreferencesLibrary(
    private val storageLibrary: StorageLibraryInterface,
    private val versionName: String,
) : PreferencesLibraryInterface {

    override suspend fun checkPermissionsShown(): Boolean {

        return storageLibrary.getBoolean(StorageLibraryKeys.PermissionsShown).first()
    }

    override suspend fun permissionsRequested() {

        storageLibrary.storeBoolean(StorageLibraryKeys.PermissionsShown, true)
    }

    override suspend fun checkDefaultDecisionOption(): String {

        return storageLibrary.getString(StorageLibraryKeys.DecisionDefault).first()
    }

    override suspend fun saveDefaultDecisionOption(key: String) {

        storageLibrary.storeString(StorageLibraryKeys.DecisionDefault, key)
    }

    override suspend fun shouldSkipDecisionType(): Boolean {

        return storageLibrary.getBoolean(StorageLibraryKeys.SkipDecisionType, defaultValue = false).first()
    }

    override suspend fun saveSkipDecisionType(enabled: Boolean) {

        storageLibrary.storeBoolean(StorageLibraryKeys.SkipDecisionType, enabled)
    }

    override fun checkVersionName(): String {

        return versionName
    }
}