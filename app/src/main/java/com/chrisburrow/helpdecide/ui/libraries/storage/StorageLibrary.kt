package com.chrisburrow.helpdecide.ui.libraries.storage

import android.content.Context
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

object StorageLibraryKeys {
    val CrashalyicsEnabled = "crashalyics_enabled"
    val AnalyticsEnabled = "analytics_enabled"
    val decisionPreference = "decision_preference"
}

interface StorageLibraryInterface {

    suspend fun storeString(key: String, value: String)
    suspend fun getString(key: String, defaultValue: String = ""): Flow<String>

    suspend fun storeBoolean(key: String, value: Boolean)
    suspend fun getBoolean(key: String, defaultValue: Boolean = false): Flow<Boolean>
}

class StorageLibrary(val context: Context): StorageLibraryInterface {

    private val Context.dataStore by preferencesDataStore(
        name = "HelpDecideStorage"
    )

    private val dataSource = context.dataStore

    override suspend fun storeString(key: String, value: String) {

        val stringKey = stringPreferencesKey(key)

        context.dataStore.edit { settings ->
            settings[stringKey] = value
        }
    }

    override suspend fun getString(key: String, defaultValue: String): Flow<String> {

        val stringKey = stringPreferencesKey(key)

        return dataSource.data.catch { exception ->
            if (exception is IOException){
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences->
            val result = preferences[stringKey]?: defaultValue
            result
        }
    }

    override suspend fun storeBoolean(key: String, value: Boolean) {

        val booleanKey = booleanPreferencesKey(key)

        context.dataStore.edit { settings ->
            settings[booleanKey] = value
        }
    }

    override suspend fun getBoolean(key: String, defaultValue: Boolean): Flow<Boolean> {

        val booleanKey = booleanPreferencesKey(key)

        return dataSource.data.catch { exception ->
            if (exception is IOException){
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences->
            val result = preferences[booleanKey]?: defaultValue
            result
        }
    }
}