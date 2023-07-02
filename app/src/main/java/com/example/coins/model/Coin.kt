package com.example.coins.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Coin(
    val id: String,
    val name: String,
    val image: String,
    @SerialName(value = "current_price")
    val currentPrice: Double,
    @SerialName(value = "price_change_24h")
    val priceChange: Double,
    @SerialName(value = "price_change_percentage_24h")
    val priceChangePercentage: Double,
)

