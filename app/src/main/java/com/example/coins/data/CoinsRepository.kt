package com.example.coins.data

import CoinsApiService
import com.example.coins.data.model.Coin
import com.example.coins.data.model.toModel

interface CoinsRepository {
    suspend fun getCoin(id: String): Coin
    suspend fun getCoins(): List<Coin>
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
}