package com.example.coins.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("data_store")

class DataStoreManager(val context: Context) {

    suspend fun saveSettings(settingsData: SettingsData) {
        context.dataStore.edit { pref ->
//            pref[stringPreferencesKey("favorites_id")] = settingsData.favoredCoin
        }
    }

//    fun getSettings() = context.dataStore.data.map { pref ->
//        return@map SettingsData(
//            pref[stringPreferencesKey("favorites_id")] ?: "",
//        pref[stringPreferencesKey("list_of_favorites")] ?: ""
//        )
}
