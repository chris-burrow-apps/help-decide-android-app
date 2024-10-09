package com.chrisburrow.helpdecide

import android.app.Application
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibrary
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface
import com.chrisburrow.helpdecide.ui.libraries.storage.StorageLibrary
import com.chrisburrow.helpdecide.ui.libraries.storage.StorageLibraryInterface

class DecideApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        storageLibrary = StorageLibrary(this)
        analyticsLibrary = AnalyticsLibrary(this, storageLibrary = storageLibrary)
    }

    companion object {

        lateinit var storageLibrary: StorageLibraryInterface
        lateinit var analyticsLibrary: AnalyticsLibraryInterface
    }
}