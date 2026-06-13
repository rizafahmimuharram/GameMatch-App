package com.rizafahmi0093.gamematch.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rizafahmi0093.gamematch.BuildConfig
import com.rizafahmi0093.gamematch.model.PostRequest
import com.rizafahmi0093.gamematch.model.PostResponse
import com.rizafahmi0093.gamematch.network.SupabaseApi
import com.rizafahmi0093.gamematch.network.SupabaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID
import com.rizafahmi0093.gamematch.network.ApiStatus


class PostViewModel(private val context: Context) : ViewModel() {

    var posts = MutableStateFlow<List<PostResponse>>(emptyList())
        private set

    var status = MutableStateFlow(ApiStatus.LOADING)
        private set

    var errorMessage = mutableStateOf<String?>(null)
        private set
    var uploadSuccess = MutableStateFlow(false)
        private set

    fun resetUploadSuccess() {
        uploadSuccess.value = false
    }


    init {
        loadPosts()
    }

    fun loadPosts() {
        viewModelScope.launch(Dispatchers.IO) {
            status.value = ApiStatus.LOADING
            try {
                posts.value = SupabaseApi.service.getPosts()
                status.value = ApiStatus.SUCCESS
            } catch (e: Exception) {
                Log.e("PostViewModel", "Error: ${e.message}")
                status.value = ApiStatus.FAILED
            }
        }
    }

    fun createPost(
        userEmail: String,
        userName: String,
        caption: String,
        imageUri: Uri?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val imageUrl = if (imageUri != null) {
                    val bytes = context.contentResolver
                        .openInputStream(imageUri)?.readBytes() ?: byteArrayOf()
                    val fileName = "${UUID.randomUUID()}.jpg"
                    SupabaseStorage.uploadImage(bytes, fileName)
                } else ""

                SupabaseApi.service.createPost(
                    PostRequest(
                        userEmail = userEmail,
                        userName = userName,
                        caption = caption,
                        imageUrl = imageUrl
                    )
                )
                loadPosts()
                uploadSuccess.value = true    // ← trigger navigate
            } catch (e: Exception) {
                Log.e("PostViewModel", "Error: ${e.message}")
                errorMessage.value = "Gagal posting: ${e.message}"
            }
        }
    }

    fun deletePost(postId: Long, userEmail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                SupabaseApi.service.deletePost(
                    id = "eq.$postId",
                    userEmail = "eq.$userEmail"
                )
                // auto-refresh
                loadPosts()
            } catch (e: Exception) {
                Log.e("PostViewModel", "Delete error: ${e.message}")
            }
        }
    }

    fun clearError() {
        errorMessage.value = null
    }
}