package com.chrisburrow.helpdecide.ui.libraries.preferences

import com.chrisburrow.helpdecide.ui.libraries.storage.StorageLibraryInterface
import com.chrisburrow.helpdecide.ui.libraries.storage.StorageLibraryKeys
import kotlinx.coroutines.flow.first

class PreferencesLibrary(
    private val storageLibrary: StorageLibraryInterface
) : PreferencesLibraryInterface {

    override suspend fun checkPermissionsShown(): Boolean {

        return storageLibrary.getBoolean(StorageLibraryKeys.PermissionsShown).first()
    }

    override suspend fun permissionsRequested() {

        storageLibrary.storeBoolean(StorageLibraryKeys.PermissionsShown, true)
    }
}