package com.example.coins.data.model

import com.example.coins.data.CoinEntity
import com.example.coins.network.model.CoinNet

data class Coin(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val currentPrice: Double = 0.0,
    val priceChange: Double = 0.0,
    val priceChangePercentage: Double = 0.0,
    var isFavorite: Boolean = false,
)

fun CoinNet.toModel(isFavorite: Boolean) =
    Coin(
        id = id,
        name = name,
        image = image,
        currentPrice = currentPrice,
        priceChange = priceChange,
        priceChangePercentage = priceChangePercentage,
        isFavorite = isFavorite,
    )

fun CoinEntity.toModel() =
    Coin(
        id = id,
        name = name,
        image = image,
        currentPrice = currentPrice,
        priceChange = priceChange,
        priceChangePercentage = priceChangePercentage,
        isFavorite = isFavorite,
    )

fun Coin.toEntity() =
    CoinEntity(
        id = id,
        name = name,
        image = image,
        currentPrice = currentPrice,
        priceChange = priceChange,
        priceChangePercentage = priceChangePercentage,
        isFavorite = isFavorite,
    )

