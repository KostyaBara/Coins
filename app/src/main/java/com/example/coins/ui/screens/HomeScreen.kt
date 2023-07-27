package com.example.coins.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coins.data.model.Coin
import com.example.coins.ui.screens.favorites.FavoritesScreen
import com.example.coins.ui.screens.favorites.FavoritesViewModel
import com.example.coins.ui.screens.list.CoinsListScreen
import com.example.coins.ui.screens.settings.SettingsScreen
import com.example.coins.ui.screens.settings.SettingsViewModel

@Composable
fun HomeScreen(
    onItemClick: (Coin) -> Unit,
    viewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory),
) {
    var tabIndex = remember { mutableStateOf(0) }

    val tabs = listOf("List", "Favorites", "Settings")

    if (viewModel.isOnFavoriteScreen) {
        viewModel.isOnFavoriteScreen = false
        tabIndex = remember { mutableStateOf(1) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        when (tabIndex.value) {
            0 -> CoinsListScreen(
                onItemClick = onItemClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
            1 ->
                FavoritesScreen(
                    onItemClick = onItemClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                )
            2 ->
                SettingsScreen(
                    viewModel = viewModel(
                        factory = SettingsViewModel.Factory
                    ),
                )
        }
        Divider()
        TabRow(
            selectedTabIndex = tabIndex.value,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .background(Color.Blue)
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex.value == index,
                    onClick = { tabIndex.value = index },
                    icon = {
                        when (index) {
                            0 -> Icon(imageVector = Icons.Default.Home, contentDescription = null)
                            1 -> Icon(imageVector = Icons.Default.Star, contentDescription = null)
                            2 -> Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                        }
                    }
                )
            }
        }
    }
}