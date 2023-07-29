package com.example.coins.ui.screens.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coins.data.UserPrefs
import com.example.coins.data.model.ThemeType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPrefs: UserPrefs,
) : ViewModel() {

    var uiState: StateFlow<SettingsUiState> =
        userPrefs.isButtonSelected.map { isButtonSelected ->
            SettingsUiState(isButtonSelected)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SettingsUiState()
            )

    fun selectTheme(themeType: ThemeType, systemMode: Boolean) {
        viewModelScope.launch {
            when (themeType) {
                ThemeType.LIGHT -> {
                    userPrefs.setDarkTheme(isDarkTheme = 0, systemMode = systemMode)
                }

                ThemeType.DARK -> {
                    userPrefs.setDarkTheme(isDarkTheme = 1, systemMode = systemMode)
                }

                ThemeType.SYSTEM -> {
                    userPrefs.setDarkTheme(isDarkTheme = 2, systemMode = systemMode)
                }
            }
        }
    }

    data class SettingsUiState(
        var isButtonSelected: Int = 3,
    )

}

