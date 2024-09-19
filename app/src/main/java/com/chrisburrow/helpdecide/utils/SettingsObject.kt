package com.chrisburrow.helpdecide.utils

class SettingsType {

    companion object {

        const val Crashlytics = "CrashlyticsRow"
        const val GoogleAnalytics = "GoogleAnalyticsRow"
        const val PushNotifications = "PushNotificationsRow"
    }
}

open class SettingsRow (
    val title: String = "",
    val description: String = "",
)

class SettingsBooleanRow(
    title: String,
    description: String,
    var enabled: Boolean = false,
    var loading: Boolean = false,
    val toggled: (Boolean) -> Unit,
) : SettingsRow(title, description)