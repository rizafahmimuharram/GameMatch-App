package com.rizafahmi0093.gamematch.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizafahmi0093.gamematch.database.MatchDb
import com.rizafahmi0093.gamematch.model.Wishlist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class WishlistViewModel(context: Context) : ViewModel() {

    private val dao = MatchDb.getInstance(context).wishlistDao()

    val wishlistItem: Flow<List<Wishlist>> = dao.getAll()

    fun isWishlisted(gameId: Int): Flow<Boolean> = dao.isWishlisted(gameId)

    fun addWishlist(wishlist: Wishlist) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(wishlist)
        }
    }

    fun removeWishlist(gameId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
        dao.deleteById(gameId)
        }
    }
}




