package com.chrisburrow.helpdecide.utils

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

class SettingsStringRow(
    title: String,
    description: String,
) : SettingsRow(title, description)