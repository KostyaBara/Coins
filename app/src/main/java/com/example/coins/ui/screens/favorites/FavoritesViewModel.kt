package com.example.coins.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coins.data.CoinsRepository
import com.example.coins.data.LoadingMode
import com.example.coins.data.model.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface FavoritesUiState {
    data class Success(val coins: List<Coin>) : FavoritesUiState
    object Empty : FavoritesUiState
    object Error : FavoritesUiState
    object Loading : FavoritesUiState
}

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val coinsRepository: CoinsRepository
) : ViewModel() {
    val uiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Loading)

    init {
        observeCoins()
    }

    fun refresh() {
        viewModelScope.launch {
            coinsRepository.getCoins(loadingMode = LoadingMode.NET)
        }
    }

    private fun observeCoins() {
        uiState.update { FavoritesUiState.Loading }

        coinsRepository
            .observeFavorite()
            .onEach { coins -> uiState.update { FavoritesUiState.Success(coins) } }
            .catch { uiState.update { FavoritesUiState.Error } }
            .launchIn(viewModelScope)

    }
}
