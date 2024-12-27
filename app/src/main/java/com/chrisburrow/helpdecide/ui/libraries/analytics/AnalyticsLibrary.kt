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
import kotlinx.coroutines.flow.first

object AnalyticsScreens {
    const val DECISION_TYPE = "decisiontype_screen"
    const val HOME = "home_screen"
    const val SETTINGS = "settings_screen"
    const val ADD_TEXT = "add_text_screen"
    const val WHEEL = "wheel_screen"
    const val INSTANT = "instant_screen"
    const val REMOVE_ALL = "remove_all_screen"
    const val ADD_ANOTHER = "add_another_screen"
    const val DECISION_CHOSEN = "decision_chosen_screen"
    const val BUBBLE_PICKER = "bubble_picker_screen"
}

object AnalyticsActions {
    const val ADD = "add_action"
    const val CANCEL = "cancel_action"
    const val CLEAR = "clear_action"
    const val REMOVE_OPTION = "remove_option_action"
    const val OPTION_PRESSED = "bubble_pressed_action"
    const val DONE = "done_action"
    const val VOICE = "voice_action"
    const val DECIDE = "decide_action"
    const val GO = "go_action"
}

class AnalyticsLibrary(
    private val context: Context,
    private val test: Boolean = false,
    private val storageLibrary: StorageLibraryInterface
) : AnalyticsLibraryInterface {

    override suspend fun getCrashalyticsState(): Boolean {

        return storageLibrary.getBoolean(StorageLibraryKeys.CrashalyicsEnabled).first()
    }

    override suspend fun getAnalyticsState(): Boolean {

        return storageLibrary.getBoolean(StorageLibraryKeys.AnalyticsEnabled).first()
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