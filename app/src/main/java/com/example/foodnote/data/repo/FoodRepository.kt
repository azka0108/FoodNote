package com.example.foodnote.data.repo

import com.example.foodnote.data.db.FoodDao
import com.example.foodnote.data.model.Food
import kotlinx.coroutines.flow.Flow

class FoodRepository(private val foodDao: FoodDao) {

    val allFood: Flow<List<Food>> = foodDao.getAllFood()

    suspend fun insert(food: Food) {
        foodDao.insertFood(food)
    }

    suspend fun update(food: Food) {
        foodDao.updateFood(food)
    }

    suspend fun delete(food: Food) {
        foodDao.deleteFood(food)
    }


}
