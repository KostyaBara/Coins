package com.example.coins.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coins.data.CoinsRepository
import com.example.coins.data.model.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    private val _item = MutableStateFlow<List<Coin>?>(null)
    val item: StateFlow<List<Coin>?>
        get() = _item.asStateFlow()

    init {
        observeCoins()
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            val coinsList = mutableListOf<List<Coin>>()
                _item.emit(coinsRepository.getCoins())
            _isRefreshing.emit(false)
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
