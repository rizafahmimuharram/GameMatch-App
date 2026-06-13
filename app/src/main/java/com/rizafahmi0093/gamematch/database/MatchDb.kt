package com.rizafahmi0093.gamematch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rizafahmi0093.gamematch.model.Match
import com.rizafahmi0093.gamematch.model.Wishlist
import com.rizafahmi0093.gamematch.model.Post
import com.rizafahmi0093.gamematch.model.Review

@Database(entities = [Match::class, Wishlist::class, Post::class, Review::class], version = 4)
abstract class MatchDb : RoomDatabase() {

    abstract fun matchDao(): MatchDao
    abstract fun wishlistDao(): WishlistDao
    abstract fun postDao(): PostDao

    abstract fun reviewDao(): ReviewDao

    companion object {
        @Volatile
        private var INSTANCE: MatchDb? = null

        fun getInstance(context: Context): MatchDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MatchDb::class.java,
                    "match.db"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}