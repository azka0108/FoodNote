package com.example.foodnote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodnote.datastore.PreferenceManager

class AuthViewModelFactory(
    private val pref: PreferenceManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
