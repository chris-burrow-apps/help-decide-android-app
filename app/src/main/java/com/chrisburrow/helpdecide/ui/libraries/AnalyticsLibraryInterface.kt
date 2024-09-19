package com.chrisburrow.helpdecide.ui.libraries

import kotlinx.coroutines.flow.Flow

interface AnalyticsLibraryInterface {

    suspend fun getCrashalyticsState() : Flow<Boolean>
    suspend fun getAnalyticsState() : Flow<Boolean>

    suspend fun setCrashalyticsState(enabled: Boolean)
    suspend fun setAnalyticsState(enabled: Boolean)
}