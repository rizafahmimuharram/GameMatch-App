package com.rizafahmi0093.gamematch.database

import androidx.room.*
import com.rizafahmi0093.gamematch.model.Match
import kotlinx.coroutines.flow.Flow

@Dao
interface MatchDao {

    @Insert
    suspend fun insert(match: Match)

    @Update
    suspend fun update(match: Match)

    @Query("DELETE FROM match WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("SELECT * FROM match ORDER BY date DESC")
    fun getAll(): Flow<List<Match>>

    @Query("SELECT * FROM match WHERE id = :id")
    suspend fun getById(id: Long): Match?
}