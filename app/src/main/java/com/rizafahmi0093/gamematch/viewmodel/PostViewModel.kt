package com.rizafahmi0093.gamematch.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizafahmi0093.gamematch.database.MatchDb
import com.rizafahmi0093.gamematch.model.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PostViewModel(context: Context) : ViewModel() {
    private val dao = MatchDb.getInstance(context).postDao()

    val allPosts: Flow<List<Post>> = dao.getAll()

    fun getPostsByUser(userName: String): Flow<List<Post>> = dao.getByUser(userName)

    fun addPost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(post)
        }
    }

    fun deletePost(post: Post) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.delete(post)
        }

    }
}
