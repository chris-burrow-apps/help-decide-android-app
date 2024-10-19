package com.chrisburrow.helpdecide.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.chrisburrow.helpdecide.ui.libraries.analytics.AnalyticsLibraryInterface

open class AnalyticsViewModel(
    private val analyticsLibrary: AnalyticsLibraryInterface
) : ViewModel() {

    fun logScreenView(screenName: String) {

        analyticsLibrary.logScreenView(screenName)
    }

    fun logButtonPressed(buttonText: String) {

        analyticsLibrary.logButtonPressed(buttonText)
    }
}