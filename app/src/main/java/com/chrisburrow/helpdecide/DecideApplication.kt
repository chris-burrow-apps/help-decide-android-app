package com.chrisburrow.helpdecide

import android.app.Application
import com.chrisburrow.helpdecide.ui.libraries.storage.StorageLibrary
import com.chrisburrow.helpdecide.ui.libraries.storage.StorageLibraryInterface

class DecideApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        storageLibrary = StorageLibrary(this)
    }

    companion object {

        lateinit var storageLibrary: StorageLibraryInterface
    }
}