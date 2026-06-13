package com.rizafahmi0093.gamematch.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizafahmi0093.gamematch.database.MatchDb
import com.rizafahmi0093.gamematch.model.Review
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ReviewViewModel(context: Context) : ViewModel() {

    private val dao = MatchDb.getInstance(context).reviewDao()

    val allReviews: Flow<List<Review>> = dao.getAll()

    fun getReviewsByGame(gameId: Int): Flow<List<Review>> = dao.getByGame(gameId)

    fun getReviewsByUser(userName: String): Flow<List<Review>> = dao.getByUser(userName)

    fun addReview(review: Review) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(review)
        }
    }

    fun deleteReview(review: Review) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(review)
        }
    }
}