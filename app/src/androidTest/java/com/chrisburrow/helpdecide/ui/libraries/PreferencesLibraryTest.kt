package com.chrisburrow.helpdecide.ui.libraries

import com.chrisburrow.helpdecide.ui.libraries.preferences.PreferencesLibrary
import com.chrisburrow.helpdecide.ui.libraries.storage.MockStorage
import com.chrisburrow.helpdecide.ui.libraries.storage.StorageLibraryKeys
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PreferencesLibraryTest {

    private lateinit var fakeStorage: MockStorage
    private lateinit var preferencesLibrary: PreferencesLibrary

    @Before
    fun setUp() {

        fakeStorage = MockStorage()
        preferencesLibrary = PreferencesLibrary(
            storageLibrary = fakeStorage
        )
    }

    @Test
    fun checkPermissionsShown() = runTest  {

        assertFalse(fakeStorage.didGetBooleanCalled(StorageLibraryKeys.PermissionsShown))

        preferencesLibrary.checkPermissionsShown()

        assertTrue(fakeStorage.didGetBooleanCalled(StorageLibraryKeys.PermissionsShown))
        assertTrue(fakeStorage.didGetBooleanCalledWithDefault(StorageLibraryKeys.PermissionsShown, false))
    }

    @Test
    fun permissionsRequested() = runTest  {

        assertNull(fakeStorage.booleanValues[StorageLibraryKeys.PermissionsShown])

        preferencesLibrary.permissionsRequested()

        assertTrue(fakeStorage.booleanValues[StorageLibraryKeys.PermissionsShown]!!)
    }
}