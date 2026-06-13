package com.rizafahmi0093.gamematch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class Comment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val postId: Long,
    val userName: String,
    val commentText: String,
    val timestamp: Long = System.currentTimeMillis()
)