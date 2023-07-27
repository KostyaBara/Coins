package com.example.coins.ui.screens.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coins.data.CoinsRepository
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
    val uiState = MutableStateFlow<CoinsUiState>(CoinsUiState.Loading)

//    private val _isRefreshing = MutableStateFlow(false)
//    val isRefreshing: StateFlow<Boolean>
//        get() = _isRefreshing.asStateFlow()
//
//    private val _item = MutableStateFlow<List<Coin>?>(null)
//    val item: StateFlow<List<Coin>?>
//        get() = _item.asStateFlow()

    init {
        getCoins()
//        refresh()
    }

//    fun refresh() {
//        viewModelScope.launch {
//           val coinsList = mutableListOf<List<Coin>>()
//           async(Dispatchers.IO) {
//               _item.emit(coinsRepository.getCoins())
//           }
//            _isRefreshing.emit(false)
//        }
//    }

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
}


