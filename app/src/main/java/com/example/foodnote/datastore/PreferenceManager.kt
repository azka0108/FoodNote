package com.example.foodnote.datastore

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context) {

    fun isLoggedInEmail(): String? = prefs.getString("EMAIL", null)
    fun isLoggedInPassword(): String? = prefs.getString("PASSWORD", null)

    private val prefs: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    fun saveUser(email: String, password: String) {
        prefs.edit().apply {
            putString("EMAIL", email)
            putString("PASSWORD", password)
            apply()
        }
    }

    fun isLoggedIn(): Boolean {
        return prefs.contains("EMAIL")
    }

    fun clear() {
        prefs.edit().clear().apply()
    }
}
