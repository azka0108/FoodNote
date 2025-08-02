package com.example.foodnote.data.db

import androidx.room.*
import com.example.foodnote.data.model.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFood(food: Food)

    @Update
    suspend fun updateFood(food: Food)

    @Delete
    suspend fun deleteFood(food: Food)

    @Query("SELECT * FROM food_table ORDER BY id DESC")
    fun getAllFood(): Flow<List<Food>>
}
