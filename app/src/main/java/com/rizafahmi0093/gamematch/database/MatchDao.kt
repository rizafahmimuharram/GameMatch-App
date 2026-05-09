package com.rizafahmi0093.gamematch.database

import androidx.room.*
import com.rizafahmi0093.gamematch.model.Match
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {

    @Insert
    suspend fun insert(matches: Match)

    @Update
    suspend fun update(matches: Match)

    @Query("DELETE FROM matches WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM matches ORDER BY date DESC")
    fun getAll(): Flow<List<Match>>

    @Query("SELECT * FROM matches WHERE id = :id")
    suspend fun getById(id: Long): Match?
}