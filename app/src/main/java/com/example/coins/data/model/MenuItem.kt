package com.example.coins.data.model

import androidx.compose.ui.graphics.Color

sealed class MenuItem(
    open val name: String,
    open val color: Color,
) {
    data class ButtonItem(
        override val name: String,
        override val color: Color,
    ) : MenuItem(name, color)

}
