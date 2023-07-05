package com.example.coins.data

import CoinsApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val coinsRepository: CoinsRepository
}

class DefaultAppContainer : AppContainer {

    private val baseUrl =
        "https://api.coingecko.com/api/v3/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: CoinsApiService by lazy {
        retrofit.create(CoinsApiService::class.java)
    }

    override val coinsRepository: CoinsRepository by lazy {
        NetworkCoinsRepository(retrofitService)
    }
}
