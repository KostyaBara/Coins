package com.example.coins.ui.theme.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coins.network.CoinsApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface CoinsUiState {
    data class Success(val photos: String) : CoinsUiState
    object Error : CoinsUiState
    object Loading : CoinsUiState
}

class CoinsViewModel : ViewModel() {
    var coinsUiState: CoinsUiState by mutableStateOf(CoinsUiState.Loading)
        private set

    init {
        getCoins()
    }

    fun getCoins() {
        viewModelScope.launch {
            coinsUiState = CoinsUiState.Loading
            coinsUiState = try {
                val listResult = CoinsApi.retrofitService.getCoinList()
                CoinsUiState.Success(
                    "Success: ${listResult.size} Mars photos retrieved"
                )
            } catch (e: IOException) {
                CoinsUiState.Error
            } catch (e: HttpException) {
                CoinsUiState.Error
            }
        }
    }
}

