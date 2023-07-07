package com.example.coins.ui.screens.favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coins.data.model.Coin

@Composable
fun FavoritesScreen(
    onItemClick: (Coin) -> Unit,
    modifier:Modifier = Modifier,
    viewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory),
) {
    val uiState = viewModel.uiState.collectAsState().value

    Column(
        modifier = modifier
            .background(Color.Yellow)
    ) {

        Text(
            text = "Favorites",
            fontSize = 20.sp
        )
    }
}