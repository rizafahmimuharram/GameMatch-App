package com.rizafahmi0093.gamematch.database

import androidx.room.*
import com.rizafahmi0093.gamematch.model.Review
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(review: Review)

    @Query("SELECT * FROM reviews ORDER BY timestamp DESC")
    fun getAll(): Flow<List<Review>>

    @Query("SELECT * FROM reviews WHERE gameId = :gameId ORDER BY timestamp DESC")
    fun getByGame(gameId: Int): Flow<List<Review>>

    @Query("SELECT * FROM reviews WHERE userName = :userName ORDER BY timestamp DESC")
    fun getByUser(userName: String): Flow<List<Review>>

    @Delete
    suspend fun delete(review: Review)
}