package com.rizafahmi0093.gamematch.database

import androidx.room.*
import com.rizafahmi0093.gamematch.model.Comment
import kotlinx.coroutines.flow.Flow

@Dao
interface CommentDao {

    @Insert
    suspend fun insert(comment: Comment)

    @Query("SELECT * FROM comments WHERE postId = :postId ORDER BY timestamp ASC")
    fun getByPost(postId: Long): Flow<List<Comment>>

    @Delete
    suspend fun delete(comment: Comment)
}