package com.rizafahmi0093.gamematch.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rizafahmi0093.gamematch.model.Match

@Database(entities = [Match::class], version = 1)
abstract class MatchDb : RoomDatabase() {

    abstract fun matchDao(): MatchDao

    companion object {
        @Volatile
        private var INSTANCE: MatchDb? = null

        fun getInstance(context: Context): MatchDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MatchDb::class.java,
                    "match.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}