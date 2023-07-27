package com.example.coins.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.coins.data.UserPrefs
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserPrefsModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        name = "theme_preferences"
    )

    @Provides
    @Singleton
    fun provideUserPrefsRepo(dataStore: DataStore<Preferences>) =
        UserPrefs(dataStore)

    @Provides
    fun provideDataStore(@ApplicationContext appContext: Context): DataStore<Preferences> =
        appContext.dataStore
}
