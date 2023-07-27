package com.example.coins.utils

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

val priceFormatter = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).apply {
    maximumFractionDigits = 8
}

val percentsFormatter = DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH)).apply {
    maximumFractionDigits = 2
}

fun Double.currentPriceFormat(): String =
    priceFormatter.format(this)

fun Double.priceChangeFormat(): String =
    priceFormatter.format(this)

fun Double.priceChangePercentageFormat(): String =
    percentsFormatter.format(this)