package com.example.coins.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPrefs(
    private val dataStore: DataStore<Preferences>,
) {

    private companion object {
        val IS_BUTTON_SELECTED = intPreferencesKey("is_button_selected")
        val IS_DARK_THEME = booleanPreferencesKey("is_dark_theme")
        const val TAG = "UserPreferencesRepo"
        const val TAG2 = "UserPreferencesRepo2"
    }

    val isButtonSelected: Flow<Int> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG2, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            preferences[IS_BUTTON_SELECTED] ?: 0
        }

    val isDarkTheme: Flow<Boolean> = dataStore.data
      .catch {
          if (it is IOException) {
              Log.e(TAG, "Error reading preferences.", it)
              emit(emptyPreferences())
          } else {
              throw it
          }
      }
      .map { preferences ->
          preferences[IS_DARK_THEME] ?: true
      }

    suspend fun setDarkTheme(isDarkTheme: Int, systemMode: Boolean) {
        when (isDarkTheme) {
            0 -> dataStore.edit { preferences ->
                preferences[IS_DARK_THEME] = false
                preferences[IS_BUTTON_SELECTED] = 0
            }

            1 -> dataStore.edit { preferences ->
                preferences[IS_DARK_THEME] = true
                preferences[IS_BUTTON_SELECTED] = 1
            }

            else -> dataStore.edit { preferences ->
                preferences[IS_DARK_THEME] = systemMode
                preferences[IS_BUTTON_SELECTED] = 2
            }
        }
    }
}