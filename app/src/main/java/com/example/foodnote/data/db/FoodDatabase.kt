package com.example.foodnote.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodnote.data.model.Food

@Database(entities = [Food::class], version = 1, exportSchema = false)
abstract class FoodDatabase : RoomDatabase() {

    abstract fun foodDao(): FoodDao

    companion object {
        @Volatile
        private var INSTANCE: FoodDatabase? = null

        fun getDatabase(context: Context): FoodDatabase {
            // Kalau instance sudah ada, langsung return
            return INSTANCE ?: synchronized(this) {
                // Kalau belum ada, buat baru
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodDatabase::class.java,
                    "food_db"   // nama file database
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
