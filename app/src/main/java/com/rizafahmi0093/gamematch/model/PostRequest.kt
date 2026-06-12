package com.rizafahmi0093.gamematch.model

import com.squareup.moshi.Json

data class PostRequest(
    @Json(name = "user_email") val userEmail: String,
    @Json(name = "user_name") val userName: String,
    val caption: String,
    @Json(name = "image_url") val imageUrl: String = ""
)