package com.rizafahmi0093.gamematch.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizafahmi0093.gamematch.database.MatchDb
import com.rizafahmi0093.gamematch.model.Comment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CommentViewModel(context: Context) : ViewModel() {

    private val dao = MatchDb.getInstance(context).commentDao()

    fun getCommentsByPost(postId: Long): Flow<List<Comment>> = dao.getByPost(postId)

    fun addComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(comment)
        }
    }

    fun deleteComment(comment: Comment) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(comment)
        }
    }
}