package com.rizafahmi0093.gamematch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class Post(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val userName: String,
    val caption: String,
    val imageUri: String = "",
    val timestamp: Long = System.currentTimeMillis()
)