package com.rizafahmi0093.gamematch.model


import com.rizafahmi0093.gamematch.R

data class Game(
    val id: Int,
    val name: String,
    val genre: String,
    val mood: String,
    val rating: Double,
    val description: String,
    val imageResId: Int,
    val isFavorite: Boolean = false

)

