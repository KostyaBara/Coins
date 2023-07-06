package com.example.coins

import android.app.Application
import com.example.coins.coin_api.Calculator
import com.example.coins.data.AppContainer
import com.example.coins.data.DefaultAppContainer

class CoinsApplication : Application() {
    lateinit var container: AppContainer
    override  fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()

        val result = Calculator().sum(1, 2)
    }
}