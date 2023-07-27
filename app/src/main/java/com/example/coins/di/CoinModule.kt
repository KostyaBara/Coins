package com.example.coins.di

import android.content.Context
import com.example.coins.data.CoinDao
import com.example.coins.data.CoinsRepository
import com.example.coins.data.CoinsRepositoryImpl
import com.example.coins.data.MainDb
import com.example.coins.network.CoinsApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoinRepoModule {

    private val baseUrl =
        "https://api.coingecko.com/api/v3/"

    @Provides
    @Singleton
    fun provideCoinsRepo(
        dao: CoinDao,
        api: CoinsApi,
    ): CoinsRepository =
        CoinsRepositoryImpl(dao = dao, api = api)

    @Provides
    @Singleton
    fun provideCoinsApi(retrofit: Retrofit): CoinsApi =
        retrofit.create(CoinsApi::class.java)

    @Provides
    @Singleton
    fun provideRetrofit(json: Json): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()

    @Provides
    @Singleton
    fun provideJson(): Json =
        Json { ignoreUnknownKeys = true }

    @Provides
    @Singleton
    fun provideCoinsDao(db: MainDb, ): CoinDao =
        db.coinDao

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext appContext: Context): MainDb =
        MainDb.createDataBase(appContext)
}
