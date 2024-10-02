package com.chrisburrow.helpdecide.ui.libraries.analytics

import android.content.Context
import android.os.Bundle
import com.chrisburrow.helpdecide.ui.libraries.storage.StorageLibraryInterface
import com.chrisburrow.helpdecide.ui.libraries.storage.StorageLibraryKeys
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.logEvent
import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow

object AnalyticsScreens {
    val DecisionType = "decisiontype_screen"
    val Home = "home_screen"
    val Settings = "settings_screen"
    val AddText = "add_text_screen"
    val Wheel = "wheel_screen"
    val Instant = "instant_screen"
    val RemoveAll = "remove_all_screen"
}

object AnalyticsActions {
    val Save = "save_action"
    val Cancel = "cancel_action"
    val Clear = "clear_action"
    val RemoveOption = "remove_option_action"
    val Done = "done_action"
    val Voice = "voice_action"
    val Decide = "decide_action"
    val Go = "go_action"
}

class AnalyticsLibrary(
    private val context: Context,
    private val test: Boolean = false,
    private val storageLibrary: StorageLibraryInterface
) : AnalyticsLibraryInterface {

    override suspend fun checkSettingsShown(): Flow<Boolean> {

        return storageLibrary.getBoolean(StorageLibraryKeys.SettingsShown)
    }

    override suspend fun settingsShown() {

        storageLibrary.storeBoolean(StorageLibraryKeys.SettingsShown, true)
    }

    override suspend fun getCrashalyticsState(): Flow<Boolean> {

        return storageLibrary.getBoolean(StorageLibraryKeys.CrashalyicsEnabled)
    }

    override suspend fun getAnalyticsState(): Flow<Boolean> {

        return storageLibrary.getBoolean(StorageLibraryKeys.AnalyticsEnabled)
    }

    override suspend fun setCrashalyticsState(enabled: Boolean) {

        storageLibrary.storeBoolean(StorageLibraryKeys.CrashalyicsEnabled, enabled)

        if(!test) {

            Firebase.crashlytics.isCrashlyticsCollectionEnabled = enabled

            if(!enabled) {

                Firebase.crashlytics.deleteUnsentReports()
            }
        }
    }

    override suspend fun setAnalyticsState(enabled: Boolean) {

        storageLibrary.storeBoolean(StorageLibraryKeys.AnalyticsEnabled, enabled)

        if(!test) {

            Firebase.analytics.setAnalyticsCollectionEnabled(enabled)

            if(!enabled) {

                Firebase.analytics.resetAnalyticsData()
            }
        }
    }



    override fun logScreenView(screenName: String) {

        if(!test) {

            val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

            val parameters = Bundle().apply {
                this.putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            }

            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
                param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
            }

            firebaseAnalytics.setDefaultEventParameters(parameters)
        }
    }

    override fun logButtonPressed(buttonText: String) {

        if(test) {

            val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_ITEM) {
                param(FirebaseAnalytics.Param.ITEM_NAME, buttonText)
            }
        }
    }
}