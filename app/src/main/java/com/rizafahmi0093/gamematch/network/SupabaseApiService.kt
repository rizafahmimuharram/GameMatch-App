package com.rizafahmi0093.gamematch.network

import com.rizafahmi0093.gamematch.BuildConfig
import com.rizafahmi0093.gamematch.model.PostRequest
import com.rizafahmi0093.gamematch.model.PostResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val okHttpClient = OkHttpClient.Builder()
    .addInterceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("apikey", BuildConfig.SUPABASE_KEY)
            .addHeader("Authorization", "Bearer ${BuildConfig.SUPABASE_KEY}")
            .addHeader("Content-Type", "application/json")
            .addHeader("Prefer", "return=representation")
            .build()
        chain.proceed(request)
    }
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BuildConfig.SUPABASE_URL + "/rest/v1/")
    .client(okHttpClient)
    .build()

interface SupabaseApiService {


    @GET("posts")
    suspend fun getPosts(
        @Header("Authorization") token: String,
        @Query("order") order: String = "created_at.desc"
    ): List<PostResponse>


    @POST("posts")
    suspend fun createPost(
        @Body post: PostRequest
    ): List<PostResponse>


    @DELETE("posts")
    suspend fun deletePost(
        @Query("id") id: String,
        @Query("user_email") userEmail: String
    )
}


object SupabaseStorage {
    fun uploadImage(
        imageBytes: ByteArray,
        fileName: String
    ): String {
        val client = OkHttpClient()
        val requestBody = imageBytes.toRequestBody("image/jpeg".toMediaType())

        val request = okhttp3.Request.Builder()
            .url("${BuildConfig.SUPABASE_URL}/storage/v1/object/post-images/$fileName")
            .addHeader("apikey", BuildConfig.SUPABASE_KEY)
            .addHeader("Authorization", "Bearer ${BuildConfig.SUPABASE_KEY}")
            .put(requestBody)
            .build()

        val response = client.newCall(request).execute()
        return if (response.isSuccessful) {
            "${BuildConfig.SUPABASE_URL}/storage/v1/object/public/post-images/$fileName"
        } else {
            ""
        }
    }
}

object SupabaseApi {
    val service: SupabaseApiService by lazy {
        retrofit.create(SupabaseApiService::class.java)
    }
}