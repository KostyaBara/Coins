package com.example.coins.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.coins.CoinsApplication
import com.example.coins.data.CoinsRepository
import com.example.coins.data.model.Coin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface FavoritesUiState {
    data class Success(val coins: List<Coin>) : FavoritesUiState
    object Empty : FavoritesUiState
    object Error : FavoritesUiState
    object Loading : FavoritesUiState
}

class FavoritesViewModel(private val coinsRepository: CoinsRepository) : ViewModel() {
    val uiState = MutableStateFlow<FavoritesUiState>(FavoritesUiState.Loading)

    init {
        getCoins()
    }

    fun getCoins() {
        viewModelScope.launch {
            uiState.update { FavoritesUiState.Loading }
            val newState = try {
                FavoritesUiState.Success(
                    coinsRepository.getCoins()
                )
            } catch (e: Throwable) {
                FavoritesUiState.Error
            }
            uiState.update { newState }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CoinsApplication)
                val coinsRepository = application.container.coinsRepository
                FavoritesViewModel(coinsRepository = coinsRepository)
            }
        }
    }
}
