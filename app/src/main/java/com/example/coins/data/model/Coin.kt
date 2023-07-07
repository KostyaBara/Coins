package com.example.coins.data.model

import com.example.coins.network.model.CoinNet

data class Coin(
    val id: String = "",
    val name: String = "",
    val image: String = "",
    val currentPrice: Double = 0.0,
    val priceChange: Double = 0.0,
    val priceChangePercentage: Double = 0.0,
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

