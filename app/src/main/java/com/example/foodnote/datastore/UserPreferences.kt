package com.example.foodnote.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Ekstensi DataStore untuk Context
val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserPreferences (private val context: Context) {

    companion object {
        private val USERNAME_KEY = stringPreferencesKey("username")
    }

    // Fungsi untuk menyimpan username
    suspend fun saveUser(username: String) {
        context.dataStore.edit { preferences ->
            preferences[USERNAME_KEY] = username
        }
    }

    // Fungsi untuk mengambil username
    val getUsername: Flow<String?> = context.dataStore.data.map { preferences ->
        preferences[USERNAME_KEY]
    }
}
