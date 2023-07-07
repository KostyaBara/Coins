package com.example.coins.network.model

import kotlinx.serialization.Serializable

@Serializable
data class CoinChartNet(
    val prices: List<ChartItemNet>,
//    val market_caps: List<ChartItem>,
//    val total_volumes: List<ChartItem>
)

@Serializable
data class ChartItemNet(
    val timestamp: Long,
    val value: Double,
)