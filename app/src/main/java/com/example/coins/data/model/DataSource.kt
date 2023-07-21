package com.example.coins.data.model

import androidx.compose.ui.graphics.Color

object DataSource {

    val modeChangeButtons = listOf(
        MenuItem.ButtonItem(
            name = "Light Mode",
            color = Color(225, 140, 84)
        ),
        MenuItem.ButtonItem(
            name = "Dark Mode",
            color = Color(56, 51, 37)
        ),
        MenuItem.ButtonItem(
            name = "System mode - as on the device",
            color = Color(214, 54, 25)
        )
    )
}