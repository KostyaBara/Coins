package com.example.coins.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coins.data.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository,
) : ViewModel() {
    var uiState: StateFlow<SettingsUiState> =
        userPreferencesRepository.isDarkTheme.map { isDarkTheme ->
            SettingsUiState(isDarkTheme)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SettingsUiState()
            )

    fun selectTheme(isDarkTheme: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.saveThemePreference(isDarkTheme)
        }
    }
}

data class SettingsUiState(
    var isDarkTheme: Boolean = true
)

