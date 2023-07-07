package com.example.coins.utils

fun Double.currentPriceFormat(): String {
    val value = String.format("%.6f", this).toDouble()
    return ("$value $")
}

fun Double.priceChangeFormat(): String {
    val value = String.format("%.6f", this).toDouble()
    return ("$value $")
}
fun Double.priceChangePercentageFormat():String {
    val priceChangePercentageFormat = String.format("%.2f", this).toDouble()
    return ("$priceChangePercentageFormat %")
}