package com.rizafahmi0093.gamematch.database

import androidx.room.*
import com.rizafahmi0093.gamematch.model.Post
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {

    @Insert
    suspend fun insert(post: Post)

    @Query("SELECT * FROM posts ORDER BY timestamp DESC")
    fun getAll(): Flow<List<Post>>

    @Query("SELECT * FROM posts WHERE userName = :userName ORDER BY timestamp DESC")
    fun getByUser(userName: String): Flow<List<Post>>

    @Delete
    suspend fun delete(post: Post)
}