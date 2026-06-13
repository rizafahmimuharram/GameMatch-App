package com.rizafahmi0093.gamematch.network

import com.rizafahmi0093.gamematch.model.TrendingGame
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://www.freetogame.com/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface TrendingApiService {
    @GET("games")
    suspend fun getTrendingGames(): List<TrendingGame>
}

object TrendingApi {
    val service: TrendingApiService by lazy {
        retrofit.create(TrendingApiService::class.java)
    }
}
