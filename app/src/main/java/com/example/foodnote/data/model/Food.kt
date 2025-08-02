package com.example.foodnote.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_table")
data class Food(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,           // teks (varchar)
    val calories: Int,          // angka (int)
    val imageUrl: String        // file/url (gambar)
)
