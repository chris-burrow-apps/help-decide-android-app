package com.chrisburrow.helpdecide.utils

open class SettingsRow (
    val title: String = "",
    val description: String = "",
    var enabled: Boolean = true,
)

class SettingsBooleanRow(
    title: String,
    description: String,
    var switchPosition: Boolean = false,
    var loading: Boolean = false,
    val toggled: (Boolean) -> Unit,
) : SettingsRow(title, description)

class SettingsStringRow(
    title: String,
    description: String,
    val clicked: () -> Unit = {},
) : SettingsRow(title, description)