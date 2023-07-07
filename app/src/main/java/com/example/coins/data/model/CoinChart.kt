package com.example.coins.data.model

import com.example.coins.network.model.CoinChartNet

data class CoinChart(
    val prices: List<ChartItem>,
)

data class ChartItem(
    val timestamp: Long,
    val value: Double,
)

fun CoinChartNet.toModel() =
    CoinChart(
        prices = prices.map { ChartItem(it.timestamp, it.value) }
    )
