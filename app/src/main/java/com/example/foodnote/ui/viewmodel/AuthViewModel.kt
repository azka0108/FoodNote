package com.example.foodnote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodnote.datastore.PreferenceManager
import kotlinx.coroutines.launch

class AuthViewModel(private val pref: PreferenceManager) : ViewModel() {

    fun saveUser(email: String, password: String) {
        viewModelScope.launch {
            pref.saveUser(email, password)
        }
    }

    fun isLoggedIn(): Boolean {
        return pref.isLoggedIn()
    }

    fun getEmail(): String? {
        return pref.isLoggedInEmail()
    }

    fun getPassword(): String? {
        return pref.isLoggedInPassword()
    }

    fun logout() {
        viewModelScope.launch {
            pref.clear()
        }
    }
}
