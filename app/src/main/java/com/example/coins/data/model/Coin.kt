package com.example.coins.data.model

import com.example.coins.network.model.CoinNet

data class Coin(
    val id: String,
    val name: String,
    val image: String,
    val currentPrice: Double,
    val priceChange: Double,
    val priceChangePercentage: Double,
)
fun CoinNet.toModel() =
    Coin(
        id = id,
        name = name,
        image = image,
        currentPrice = currentPrice,
        priceChange = priceChange,
        priceChangePercentage = priceChangePercentage,
    )

