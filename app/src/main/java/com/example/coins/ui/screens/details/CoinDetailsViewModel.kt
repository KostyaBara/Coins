package com.example.coins.ui.screens.details//package com.example.coins.ui.theme.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.coins.CoinsApplication
import com.example.coins.data.CoinsRepository
import com.example.coins.data.model.Coin
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface CoinDetailsUiState {
    data class Success(
        val coin: Coin = Coin(),
        val chart: List<Float> = listOf(
            4.33F, 500F, 5.32F, 15.1F, 82.6F, 12F, 55F, 100F, 60F, 26F, 13F, 34F, 55F, 139F, 35F, 79F
        ),
        ) : CoinDetailsUiState
    object Error : CoinDetailsUiState
    object Loading : CoinDetailsUiState
}

class CoinDetailsViewModel(
    private val coinId: String?,
    private val coinsRepository: CoinsRepository,
) : ViewModel() {
    val uiState = MutableStateFlow<CoinDetailsUiState>(CoinDetailsUiState.Loading)

    init {
        getCoinDetails()
        observeCoin()
    }

    fun setFavorite(isFavorite: Boolean) =
        viewModelScope.launch {
            coinsRepository.setFavorite(coinId!!, isFavorite)
        }

    private fun getCoinDetails() {
        viewModelScope.launch {
            uiState.update { CoinDetailsUiState.Loading }
            val newState = try {
                CoinDetailsUiState.Success(
                    coinsRepository.getCoin(coinId!!)!!
                )
            } catch (e: Throwable) {
                CoinDetailsUiState.Error
            }
            uiState.update { newState }
        }
    }

    private fun observeCoin() =
        coinsRepository.observeCoin(coinId!!)
            .onEach { coin->
                uiState.update { CoinDetailsUiState.Success(coin)  }
            }
            .launchIn(viewModelScope)

    companion object {
        fun factory(coinId: String?): ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CoinsApplication)
                val coinsRepository = application.container.coinsRepository
                CoinDetailsViewModel(
                    coinId = coinId,
                    coinsRepository = coinsRepository
                )
            }
        }
    }
}
