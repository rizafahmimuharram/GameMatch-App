package com.rizafahmi0093.gamematch.util

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.Preferences


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings_preferences"
)

class SettingsDataStore(private val context: Context) {

    companion object {
        private val IS_LIST = booleanPreferencesKey("is_list")
    }

    val layoutFlow: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[IS_LIST] ?: true
    }

    suspend fun saveLayout(isList: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[IS_LIST] = isList
        }
    }

    private val IS_DARK_MODE =
        booleanPreferencesKey("is_dark_mode")

    val themeFlow: Flow<Boolean> =
        context.dataStore.data.map { preferences ->

            preferences[IS_DARK_MODE] ?: false
        }

    suspend fun saveTheme(isDark: Boolean) {

        context.dataStore.edit { preferences ->

            preferences[IS_DARK_MODE] = isDark
        }
    }
}