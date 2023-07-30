package com.example.coins.data

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.coins.data.model.ThemeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class UserPrefs(
    private val dataStore: DataStore<Preferences>,
) {

    private companion object {
        val THEME_TYPE_KEY = intPreferencesKey("THEME_TYPE_KEY")
        const val TAG = "UserPreferencesRepo"
        const val TAG2 = "UserPreferencesRepo2"
    }

    val themeType: Flow<ThemeType> = dataStore.data
        .catch {
            if (it is IOException) {
                Log.e(TAG, "Error reading preferences.", it)
                emit(emptyPreferences())
            } else {
                throw it
            }
        }
        .map { preferences ->
            ThemeType.fromCode(preferences[THEME_TYPE_KEY] ?: ThemeType.LIGHT.code)
        }

    suspend fun setThemeType(themeType: ThemeType) {
        dataStore.edit { preferences ->
            preferences[THEME_TYPE_KEY] = themeType.code
        }
    }
}