package com.chrisburrow.helpdecide.ui.libraries

import com.chrisburrow.helpdecide.ui.libraries.preferences.PreferencesLibrary
import com.chrisburrow.helpdecide.ui.libraries.storage.MockStorage
import com.chrisburrow.helpdecide.ui.libraries.storage.StorageLibraryKeys
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PreferencesLibraryTest {

    private val expectedVersionName = System.currentTimeMillis().toString()

    private lateinit var fakeStorage: MockStorage
    private lateinit var preferencesLibrary: PreferencesLibrary

    @Before
    fun setUp() {

        fakeStorage = MockStorage()
        preferencesLibrary = PreferencesLibrary(
            storageLibrary = fakeStorage,
            versionName = expectedVersionName
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

    @Test
    fun decisionDefaultSaved() = runTest  {

        val expectedKey = "expectedKey"

        assertNull(fakeStorage.stringValues[StorageLibraryKeys.DecisionDefault])

        preferencesLibrary.saveDefaultDecisionOption(expectedKey)

        assertEquals(expectedKey, fakeStorage.stringValues[StorageLibraryKeys.DecisionDefault]!!)
    }

    @Test
    fun decisionDefaultRequested() = runTest  {

        val expectedKey = "expectedKey"

        fakeStorage.stringValues[StorageLibraryKeys.DecisionDefault] = expectedKey

        assertFalse(fakeStorage.didGetStringCalledWithDefault(StorageLibraryKeys.DecisionDefault, ""))

        preferencesLibrary.checkDefaultDecisionOption()

        assertTrue(fakeStorage.didGetStringCalledWithDefault(StorageLibraryKeys.DecisionDefault, ""))
        assertEquals(expectedKey, fakeStorage.stringValues[StorageLibraryKeys.DecisionDefault]!!)
    }

    @Test
    fun shouldSkipDecisionTypeSaved() = runTest  {

        val expectedKey = true

        assertNull(fakeStorage.stringValues[StorageLibraryKeys.SkipDecisionType])

        preferencesLibrary.saveSkipDecisionType(expectedKey)

        assertEquals(expectedKey, fakeStorage.booleanValues[StorageLibraryKeys.SkipDecisionType]!!)
    }

    @Test
    fun skipDecisionType() = runTest  {

        val expectedKey = false

        fakeStorage.booleanValues[StorageLibraryKeys.SkipDecisionType] = expectedKey

        assertFalse(fakeStorage.didGetBooleanCalledWithDefault(StorageLibraryKeys.SkipDecisionType, false))

        preferencesLibrary.shouldSkipDecisionType()

        assertTrue(fakeStorage.didGetBooleanCalledWithDefault(StorageLibraryKeys.SkipDecisionType, false))
        assertEquals(expectedKey, fakeStorage.booleanValues[StorageLibraryKeys.SkipDecisionType]!!)
    }

    @Test
    fun checkVersionPassed() = runTest {
        
        assertEquals(expectedVersionName, preferencesLibrary.checkVersionName())
    }
}