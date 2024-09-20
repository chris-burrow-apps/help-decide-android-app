package com.chrisburrow.helpdecide.ui.libraries

import com.chrisburrow.helpdecide.ui.mock.MockStorage
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

        assertFalse(fakeStorage.didGetBooleanCalled(StorageLibraryKeys.AnalyticsEnabled))

        analyticsLibrary.getAnalyticsState()

        assertTrue(fakeStorage.didGetBooleanCalled(StorageLibraryKeys.AnalyticsEnabled))
        assertTrue(fakeStorage.didGetBooleanCalledWithDefault(StorageLibraryKeys.AnalyticsEnabled, false))
    }

    @Test
    fun getCrashalyticsState() = runTest  {

        assertFalse(fakeStorage.didGetBooleanCalled(StorageLibraryKeys.CrashalyicsEnabled))

        analyticsLibrary.getCrashalyticsState()

        assertTrue(fakeStorage.didGetBooleanCalled(StorageLibraryKeys.CrashalyicsEnabled))
        assertTrue(fakeStorage.didGetBooleanCalledWithDefault(StorageLibraryKeys.CrashalyicsEnabled, false))
    }
}