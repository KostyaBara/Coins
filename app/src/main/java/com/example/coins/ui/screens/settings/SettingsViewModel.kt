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
        userPrefs.themeType.map { themeType ->
            SettingsUiState(themeType)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = SettingsUiState()
            )

    fun selectTheme(themeType: ThemeType) {
        viewModelScope.launch {
            userPrefs.setThemeType(themeType)
        }
    }

    data class SettingsUiState(
        var themeType: ThemeType = ThemeType.LIGHT,
    )

}

