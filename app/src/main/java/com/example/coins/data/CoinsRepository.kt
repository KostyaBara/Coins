package com.example.coins.data

import com.example.coins.data.model.Coin
import com.example.coins.data.model.CoinChart
import com.example.coins.data.model.toModel
import com.example.coins.network.CoinsApiService

interface CoinsRepository {
    suspend fun getCoin(id: String): Coin
    suspend fun getCoins(): List<Coin>
    suspend fun getCoinChart(id: String): CoinChart
}

class NetworkCoinsRepository(
    private val coinsApiService: CoinsApiService,
) : CoinsRepository {

    var coins = listOf<Coin>()

    override suspend fun getCoin(id: String) =
        coins.first { it.id == id }

    override suspend fun getCoins(): List<Coin> {
        coins = coinsApiService.getCoinList().map { it.toModel() }
        return coins
    }

    override suspend fun getCoinChart(id: String): CoinChart =
        coinsApiService.getCoinChart(id).toModel()
}