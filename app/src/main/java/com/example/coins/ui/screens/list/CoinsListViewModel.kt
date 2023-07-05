package com.example.coins.ui.screens.list

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

sealed interface CoinsUiState {
    data class Success(val coins: List<Coin>) : CoinsUiState
    object Error : CoinsUiState
    object Loading : CoinsUiState
}

class CoinsListViewModel(private val coinsRepository: CoinsRepository) : ViewModel() {
    val uiState = MutableStateFlow<CoinsUiState>(CoinsUiState.Loading)

    init {
        getCoins()
    }

    fun getCoins() {
        viewModelScope.launch {
            uiState.update { CoinsUiState.Loading }
            val newState = try {
                CoinsUiState.Success(
                    coinsRepository.getCoins()
                )
            } catch (e: Throwable) {
                CoinsUiState.Error
            }
            uiState.update { newState }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as CoinsApplication)
                val coinsRepository = application.container.coinsRepository
                CoinsListViewModel(coinsRepository = coinsRepository)
            }
        }
    }
}
