package com.example.coins.ui.screens.details//package com.example.coins.ui.theme.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.coins.CoinsApplication
import com.example.coins.data.CoinsRepository
import com.example.coins.data.model.Coin
import com.example.coins.ui.chart.ChartData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface CoinDetailsUiState {
    data class Success(
        val coin: Coin = Coin(),
        val chartData: ChartData,
    ) : CoinDetailsUiState

    object Error : CoinDetailsUiState
    object Loading : CoinDetailsUiState
}

class CoinDetailsViewModel(
    private val coinId: String?,
    private val coinsRepository: CoinsRepository,
) : ViewModel() {
    val uiState = MutableStateFlow<CoinDetailsUiState>(CoinDetailsUiState.Loading)

    val chart
        get() = (uiState.value as? CoinDetailsUiState.Success)?.chartData

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
//                Log.d("abcd", "loading coin")
                val coin = coinsRepository.getCoin(coinId!!)!!
//                Log.d("abcd", "loading chart")
                val chart = coinsRepository.getCoinChart(coinId).prices.map { it.value }
                Log.d("abcd", "loading ChartData")
                val chartData =
                    ChartData(coinsRepository.getCoinChart(coinId).prices.map { ChartData.Entry(
                        it.value.toBigDecimal()) })
                CoinDetailsUiState.Success(
                    coin, chartData
                )
            } catch (e: Throwable) {
                Log.e("abcd", "error", e)
                CoinDetailsUiState.Error
            }
            uiState.update { newState }
        }
    }

    private fun observeCoin() =
        coinsRepository.observeCoin(coinId!!)
            .drop(1)
            .onEach { coin ->
                uiState.update { CoinDetailsUiState.Success(coin, chart ?: ChartData()) }
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
