package com.rizafahmi0093.gamematch.model

import com.squareup.moshi.Json

data class TrendingGame(
    val id: Int,
    val title: String,
    val genre: String,
    @Json(name = "thumbnail") val thumbnail: String,
    @Json(name = "short_description") val shortDescription: String,
    val platform: String
)