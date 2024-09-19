package com.chrisburrow.helpdecide.ui.libraries

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AnalyticsLibraryTest {

    private lateinit var fakeStorage: MockStorage
    private lateinit var analyticsLibrary: AnalyticsLibrary

    @Before
    fun setUp() {

        fakeStorage = MockStorage()
        analyticsLibrary = AnalyticsLibrary(true, fakeStorage)
    }

    @Test
    fun getAnalyticsState() = runTest  {

        assertNull(fakeStorage.getBooleanCalledWithKey)
        assertNull(fakeStorage.getBooleanCalledWithValue)

        analyticsLibrary.getAnalyticsState()

        assertEquals(StorageLibraryKeys.AnalyticsEnabled, fakeStorage.getBooleanCalledWithKey)
        assertFalse(fakeStorage.getBooleanCalledWithValue!!)
    }

    @Test
    fun getCrashalyticsState() = runTest  {

        assertNull(fakeStorage.getBooleanCalledWithKey)
        assertNull(fakeStorage.getBooleanCalledWithValue)

        analyticsLibrary.getCrashalyticsState()

        assertEquals(StorageLibraryKeys.CrashalyicsEnabled, fakeStorage.getBooleanCalledWithKey)
        assertFalse(fakeStorage.getBooleanCalledWithValue!!)
    }

    @Test
    fun setAnalyticsState() = runTest  {

        assertNull(fakeStorage.storeBooleanCalledWithKey)
        assertNull(fakeStorage.storeBooleanCalledWithValue)

        analyticsLibrary.setAnalyticsState(true)

        assertEquals(StorageLibraryKeys.AnalyticsEnabled, fakeStorage.storeBooleanCalledWithKey)
        assertTrue(fakeStorage.storeBooleanCalledWithValue!!)
    }

    @Test
    fun setCrashalyticsState() = runTest  {

        assertNull(fakeStorage.storeBooleanCalledWithKey)
        assertNull(fakeStorage.storeBooleanCalledWithValue)

        analyticsLibrary.setCrashalyticsState(true)

        assertEquals(StorageLibraryKeys.CrashalyicsEnabled, fakeStorage.storeBooleanCalledWithKey)
        assertTrue(fakeStorage.storeBooleanCalledWithValue!!)
    }

    class MockStorage : StorageLibraryInterface {

        var getStringCalledWithKey: String? = null
        var getStringCalledWithValue: String? = null

        var getBooleanCalledWithKey: String? = null
        var getBooleanCalledWithValue: Boolean? = null

        var storeStringCalledWithKey: String? = null
        var storeStringCalledWithValue: String? = null

        var storeBooleanCalledWithKey: String? = null
        var storeBooleanCalledWithValue: Boolean? = null

        override suspend fun storeString(key: String, value: String) {

            storeStringCalledWithKey = key
            storeStringCalledWithValue = value
        }

        override suspend fun getString(key: String, defaultValue: String): Flow<String> {

            getStringCalledWithKey = key
            getStringCalledWithValue = defaultValue

            return flow {

                emit(defaultValue)
            }
        }

        override suspend fun storeBoolean(key: String, value: Boolean) {

            storeBooleanCalledWithKey = key
            storeBooleanCalledWithValue = value
        }

        override suspend fun getBoolean(key: String, defaultValue: Boolean): Flow<Boolean> {

            getBooleanCalledWithKey = key
            getBooleanCalledWithValue = defaultValue

            return flow {

                emit(defaultValue)
            }
        }
    }
}