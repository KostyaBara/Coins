package com.example.coins

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.coins.data.AppContainer
import com.example.coins.data.DefaultAppContainer
import com.example.coins.data.UserPreferencesRepository

private const val THEME_PREFERENCE_NAME = "theme_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = THEME_PREFERENCE_NAME
)

class CoinsApplication : Application() {

    lateinit var container: AppContainer

    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)

        container = DefaultAppContainer(this)
    }

}



