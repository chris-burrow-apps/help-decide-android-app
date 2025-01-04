package com.chrisburrow.helpdecide.utils

open class SettingsRow (
    val title: String = "",
    val description: String = "",
)

class SettingsBooleanRow(
    title: String,
    description: String,
    val enabled: Boolean = true,
    var switchPosition: Boolean = false,
    val loading: Boolean = false,
    val toggled: (Boolean) -> Unit,
) : SettingsRow(title, description)

class SettingsStringRow(
    title: String,
    description: String,
    val clicked: () -> Unit = {},
) : SettingsRow(title, description)