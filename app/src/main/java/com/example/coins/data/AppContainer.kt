package com.example.coins.data

import android.content.Context
import com.example.coins.network.CoinsApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val coinsRepository: CoinsRepository
}

class DefaultAppContainer(
    private val appContext: Context,
) : AppContainer {

    private val baseUrl =
        "https://api.coingecko.com/api/v3/"

    private val json = Json { ignoreUnknownKeys = true }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val coinsApi: CoinsApi by lazy {
        retrofit.create(CoinsApi::class.java)
    }

    private val db by lazy { MainDb.createDataBase(appContext) }

    override val coinsRepository: CoinsRepository by lazy {
        CoinsRepositoryImpl(
            dao = db.coinDao,
            api = coinsApi
        )
    }
}
