package com.example.coins.data.model

import com.example.coins.network.model.CoinChartNet

data class CoinChart(
    val prices: List<ChartItem> = emptyList(),
)

data class ChartItem(
    val timestamp: Long,
    val value: Double,
)

fun CoinChartNet.toModel() =
    CoinChart(
        prices = prices.map { ChartItem(it[0].toLong(), it[1]) }
    )
