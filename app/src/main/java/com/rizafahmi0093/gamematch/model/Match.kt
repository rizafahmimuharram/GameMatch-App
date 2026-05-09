    package com.rizafahmi0093.gamematch.model

    import androidx.room.Entity
    import androidx.room.PrimaryKey

    @Entity(tableName = "matches")
    data class Match(
        @PrimaryKey(autoGenerate = true)
        val id: Long = 0L,
        val player1: String,
        val player2: String,
        val score: String,
        val date: String
    )