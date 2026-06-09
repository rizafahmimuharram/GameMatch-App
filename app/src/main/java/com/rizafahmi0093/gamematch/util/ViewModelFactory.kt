package com.rizafahmi0093.gamematch.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rizafahmi0093.gamematch.database.MatchDb
import com.rizafahmi0093.gamematch.viewmodel.DetailViewModel
import com.rizafahmi0093.gamematch.viewmodel.MainViewModel
import com.rizafahmi0093.gamematch.viewmodel.WishlistViewModel

class ViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        val dao = MatchDb.getInstance(context).matchDao()

        return when {

            modelClass.isAssignableFrom(
                MainViewModel::class.java
            ) -> {
                MainViewModel(dao) as T
            }

            modelClass.isAssignableFrom(
                DetailViewModel::class.java
            ) -> {
                DetailViewModel(dao) as T
            }
            modelClass.isAssignableFrom(WishlistViewModel::class.java) -> {
                WishlistViewModel(context) as T
            }

            else -> {
                throw IllegalArgumentException(
                    "Unknown ViewModel class"
                )
            }
        }
    }
}