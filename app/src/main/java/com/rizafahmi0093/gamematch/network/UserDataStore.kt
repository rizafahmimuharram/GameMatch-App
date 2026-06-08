package com.rizafahmi0093.gamematch.network

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.rizafahmi0093.gamematch.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.userDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "user_preference"
)

class UserDataStore(private val context: Context) {

    companion object {
        private val USER_NAME = stringPreferencesKey("name")
        private val USER_EMAIL = stringPreferencesKey("email")
        private val USER_PHOTO = stringPreferencesKey("photoUrl")
        private val USER_CUSTOM_NAME = stringPreferencesKey("customName")
    }

    val userFlow: Flow<User> = context.userDataStore.data.map { preferences ->
        User(
            name = preferences[USER_NAME] ?: "",
            email = preferences[USER_EMAIL] ?: "",
            photoUrl = preferences[USER_PHOTO] ?: "",
            customName = preferences[USER_CUSTOM_NAME] ?: ""
        )
    }

    suspend fun saveData(user: User) {
        context.userDataStore.edit { preferences ->
            preferences[USER_NAME] = user.name
            preferences[USER_EMAIL] = user.email
            preferences[USER_PHOTO] = user.photoUrl
            preferences[USER_CUSTOM_NAME] = user.customName
        }
    }

    suspend fun updateCustomName(name: String) {
        context.userDataStore.edit { preferences ->
            preferences[USER_CUSTOM_NAME] = name
        }
    }
}