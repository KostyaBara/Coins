package com.example.coins.network

import com.example.coins.model.Coin
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL =
    "https://api.coingecko.com/api/v3/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()


interface CoinsApiService {
    @GET("coins/markets")
    suspend fun getCoinList(
        @Query("vs_currency") currency: String = "usd",
    ): List<Coin>
}

object CoinsApi {
    val retrofitService: CoinsApiService by lazy {
        retrofit.create(CoinsApiService::class.java)
    }
}