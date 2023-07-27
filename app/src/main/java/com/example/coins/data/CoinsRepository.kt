package com.example.coins.data

import com.example.coins.data.model.Coin
import com.example.coins.data.model.CoinChart
import com.example.coins.data.model.toEntity
import com.example.coins.data.model.toModel
import com.example.coins.network.CoinsApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class LoadingMode {
    NET,
    CACHE,
    CACHE_NET
}

interface CoinsRepository {
    suspend fun getCoin(id: String): Coin?
    suspend fun getCoins(loadingMode: LoadingMode = LoadingMode.CACHE_NET): List<Coin>
    suspend fun getCoinChart(id: String): CoinChart

    fun observeCoin(id:String): Flow<Coin>
    fun observeAllCoins(): Flow<List<Coin>>
    fun observeFavorite(): Flow<List<Coin>>

    suspend fun setFavorite(id: String, isFavorite: Boolean)
}

class CoinsRepositoryImpl(
    private val dao: CoinDao,
    private val api: CoinsApi,
) : CoinsRepository {

    override suspend fun getCoin(id: String) =
        dao.getCoin(id)?.toModel()

    override suspend fun getCoins(loadingMode: LoadingMode): List<Coin> =
        when (loadingMode) {
            LoadingMode.NET -> getFromNet()
            LoadingMode.CACHE -> dao.getAll().map { it.toModel() }
            LoadingMode.CACHE_NET -> dao.getAll().map { it.toModel() }.ifEmpty { getFromNet() }
        }

    private suspend fun getFromNet(): List<Coin> {
        val coins = api.getCoinList().map {
            val isFavorite = dao.isFavorite(it.id)
            it.toModel(isFavorite ?: false)
        }

        dao.insert(coins.map { it.toEntity() })

        return coins
    }

    override fun observeCoin(id: String): Flow<Coin> =
        dao.observeCoin(id).map { it.toModel() }

    override fun observeAllCoins(): Flow<List<Coin>> =
        dao.observeAll().map { list -> list.map { it.toModel() } }

    override fun observeFavorite(): Flow<List<Coin>> =
        dao.observeFavorite().map { list -> list.map { it.toModel() } }

    override suspend fun getCoinChart(id: String): CoinChart =
        api.getCoinChart(id).toModel()

    override suspend fun setFavorite(id: String, isFavorite: Boolean) {
        dao.updateFavorite(id, isFavorite)
    }
}
