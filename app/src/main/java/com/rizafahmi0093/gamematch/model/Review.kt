package com.rizafahmi0093.gamematch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reviews")
data class Review(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val gameId: Int,
    val userEmail: String = "",
    val gameName: String,
    val userName: String,
    val reviewText: String,
    val rating: Int,
    val timestamp: Long = System.currentTimeMillis()
)