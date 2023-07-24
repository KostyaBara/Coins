package com.example.coins.data.model

import androidx.compose.ui.graphics.Color

object DataSource {

    val modeChangeButtons = listOf(
        MenuItem.ButtonItem(
            name = "Light mode",
            color = Color(225, 140, 84),
            label = "☀️   "
        ),
        MenuItem.ButtonItem(
            name = "Dark mode",
            color = Color(56, 51, 37),
            label = "🌘   "
        ),
        MenuItem.ButtonItem(
            name = "System mode - as on the device",
            color = Color(214, 54, 25),
            label = "🤖   "
        )
    )
}