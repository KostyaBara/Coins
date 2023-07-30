package com.example.coins.data.model

import androidx.compose.ui.graphics.Color

enum class ThemeType(
    val title: String,
    val color: Color,
    val code: Int,
) {
    LIGHT(
        title = "☀️   Light mode",
        color = Color(225, 140, 84),
        code = 0,
    ),
    DARK(
        title = "\uD83C\uDF18   Dark mode",
        color = Color(56, 51, 37),
        code = 1,
    ),
    SYSTEM(
        title = "\uD83E\uDD16 System mode",
        color = Color(214, 54, 25),
        code = 2,
    );

    fun isDark(isSystemInDarkMode: Boolean) =
        when (this) {
            LIGHT -> false
            DARK -> true
            SYSTEM -> isSystemInDarkMode
        }


    companion object {
        fun fromCode(code: Int): ThemeType =
            ThemeType.values().first { it.code == code }
    }
}
