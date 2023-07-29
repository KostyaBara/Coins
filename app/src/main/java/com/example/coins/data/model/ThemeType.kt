package com.example.coins.data.model

import androidx.compose.ui.graphics.Color

enum class ThemeType(
    val title: String,
    val color: Color,
) {
    LIGHT(
        title = "☀️   Light mode",
        color = Color(225, 140, 84),
    ),
    DARK(
        title = "\uD83C\uDF18   Dark mode",
        color = Color(56, 51, 37),
    ),
    SYSTEM(
        title = "\uD83E\uDD16 System mode",
        color = Color(214, 54, 25),
    ),
}
