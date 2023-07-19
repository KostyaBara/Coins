package com.example.coins.ui.chart

import java.math.BigDecimal

data class ChartData(
    val entries: List<Entry> = emptyList(),
) {
    data class Entry(
        val value: BigDecimal,
        val data: Any? = null,
        val data1: Any? = null,
    )
}

