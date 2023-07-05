package com.example.coins

import android.app.Application
import com.example.coins.data.AppContainer
import com.example.coins.data.DefaultAppContainer

class CoinsApplication : Application() {
    lateinit var container: AppContainer
    override  fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}