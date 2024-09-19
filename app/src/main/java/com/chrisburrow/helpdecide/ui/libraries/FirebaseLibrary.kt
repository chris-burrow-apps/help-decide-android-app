package com.chrisburrow.helpdecide.ui.libraries

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.BuildConfig
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

class AnalyticsLibrary(private val debug: Boolean = BuildConfig.DEBUG, private val storageLibrary: StorageLibraryInterface) : AnalyticsLibraryInterface {

    override suspend fun getCrashalyticsState(): Flow<Boolean> {

        return storageLibrary.getBoolean(StorageLibraryKeys.CrashalyicsEnabled)
    }

    override suspend fun getAnalyticsState(): Flow<Boolean> {

        return storageLibrary.getBoolean(StorageLibraryKeys.AnalyticsEnabled)
    }

    override suspend fun setCrashalyticsState(enabled: Boolean) {

        storageLibrary.storeBoolean(StorageLibraryKeys.CrashalyicsEnabled, enabled)

        if(!debug) {

            Firebase.crashlytics.isCrashlyticsCollectionEnabled = enabled
        }
    }

    override suspend fun setAnalyticsState(enabled: Boolean) {

        storageLibrary.storeBoolean(StorageLibraryKeys.AnalyticsEnabled, enabled)

        if(!debug) {

            Firebase.analytics.setAnalyticsCollectionEnabled(enabled)
        }
    }
}