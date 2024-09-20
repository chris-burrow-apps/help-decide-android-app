package com.chrisburrow.helpdecide.ui.libraries.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockStorage(
    val stringValues: MutableMap<String, String> = mutableMapOf(),
    val booleanValues: MutableMap<String, Boolean> = mutableMapOf(),
) : StorageLibraryInterface {

        var keyCalledWithDefaultString = mutableMapOf<String, String>()
        var keyCalledWithDefaultBoolean = mutableMapOf<String, Boolean>()

        override suspend fun storeString(key: String, value: String) {

            stringValues[key] = value
        }

        override suspend fun getString(key: String, defaultValue: String): Flow<String> {

            keyCalledWithDefaultString.put(key, defaultValue)

            return flow {

                emit(stringValues[key] ?: defaultValue)
            }
        }

        override suspend fun storeBoolean(key: String, value: Boolean) {

            booleanValues[key] = value
        }

        override suspend fun getBoolean(key: String, defaultValue: Boolean): Flow<Boolean> {

            keyCalledWithDefaultBoolean[key] = defaultValue

            return flow {

                emit(booleanValues[key] ?: defaultValue)
            }
        }

        fun didGetStringCalled(key: String) : Boolean {

            return keyCalledWithDefaultString.contains(key)
        }

        fun didGetBooleanCalled(key: String) : Boolean {

            return keyCalledWithDefaultBoolean.contains(key)
        }

        fun didGetStringCalledWithDefault(key: String, value: String) : Boolean {

            return keyCalledWithDefaultString[key].equals(value)
        }

        fun didGetBooleanCalledWithDefault(key: String, value: Boolean) : Boolean {

            return keyCalledWithDefaultBoolean[key] == value
        }

}