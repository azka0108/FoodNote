package com.example.foodnote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodnote.data.model.Food
import com.example.foodnote.data.repo.FoodRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FoodViewModel(private val repository: FoodRepository) : ViewModel() {

    // StateFlow untuk daftar makanan (auto-update)
    val foodList: StateFlow<List<Food>> = repository.allFood
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun insertFood(food: Food) {
        viewModelScope.launch {
            repository.insert(food)
        }
    }

    fun updateFood(food: Food) {
        viewModelScope.launch {
            repository.update(food)
        }
    }

    fun deleteFood(food: Food) {
        viewModelScope.launch {
            repository.delete(food)
        }
    }


}
