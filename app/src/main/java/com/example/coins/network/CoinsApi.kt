package com.example.coins.network

import com.example.coins.network.model.CoinChartNet
import com.example.coins.network.model.CoinNet
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinsApi {
    @GET("coins/markets")
    suspend fun getCoinList(
        @Query("vs_currency") currency: String = "usd",
    ): List<CoinNet>

    @GET("coins/{id}/market_chart")
    suspend fun getCoinChart(
        @Path("id") id: String,
        @Query("vs_currency") currency: String = "usd",
        @Query("days") days: Int = 7,
    ): CoinChartNet
}
