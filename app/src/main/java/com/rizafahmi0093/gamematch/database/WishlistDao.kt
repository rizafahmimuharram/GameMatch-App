package com.rizafahmi0093.gamematch.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rizafahmi0093.gamematch.model.Wishlist
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(wishlist: Wishlist)

    @Query("DELETE FROM wishlist WHERE gameId = :gameId")
    suspend fun deleteById(gameId: Int)

    @Query("SELECT * FROM wishlist ORDER BY gameName ASC")
    fun getAll(): Flow<List<Wishlist>>

    @Query("SELECT EXISTS(SELECT 1 FROM wishlist WHERE gameId = :gameId)")
    fun isWishlisted(gameId: Int): Flow<Boolean>
}