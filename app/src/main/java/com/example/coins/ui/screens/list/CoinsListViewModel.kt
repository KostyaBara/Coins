package com.example.coins.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coins.data.CoinsRepository
import com.example.coins.data.LoadingMode
import com.example.coins.data.model.Coin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CoinsUiState {
    data class Success(val coins: List<Coin>) : CoinsUiState
    object Error : CoinsUiState
    object Loading : CoinsUiState
}

@HiltViewModel
class CoinsListViewModel @Inject constructor(
    private val coinsRepository: CoinsRepository,
) : ViewModel() {
    val uiState = MutableStateFlow<CoinsUiState?>(null)

    init {
        getCoins()
    }

    fun getCoins(loadingMode: LoadingMode = LoadingMode.CACHE_NET) {
        if (uiState.value is CoinsUiState.Loading){
            return
        }

        viewModelScope.launch {
            uiState.update { CoinsUiState.Loading }
            val newState = try {
                CoinsUiState.Success(
                    coinsRepository.getCoins(loadingMode)
                )
            } catch (e: Throwable) {
                CoinsUiState.Error
            }
            uiState.update { newState }
        }
    }
}


