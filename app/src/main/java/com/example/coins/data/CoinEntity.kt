package com.example.coins.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Coins")
data class CoinEntity(
    @PrimaryKey
    val id: String = "null",
    val name: String = "",
    val image: String = "",
    val currentPrice: Double = 0.0,
    val priceChange: Double = 0.0,
    val priceChangePercentage: Double = 0.0,
    var isFavorite: Boolean = true
)