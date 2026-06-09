package com.rizafahmi0093.gamematch.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist")
data class Wishlist(
    @PrimaryKey
    val gameId: Int,
    val gameName: String,
    val genre: String,
    val rating: Double,
    val imageResId: Int
)