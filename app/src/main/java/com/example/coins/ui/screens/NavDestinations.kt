package com.example.coins.ui.screens

import androidx.navigation.NavBackStackEntry

sealed interface NavDestinations {
    val screenName: String

    object CoinsList: NavDestinations {
        override val screenName: String
            get() = "list"
    }

    object CoinDetails: NavDestinations {

        private const val COIN_ID = "coin_id"

        fun getCoinId(backstackEntry: NavBackStackEntry) =
            backstackEntry.arguments?.getString(COIN_ID)

        fun withId(coinId:String) =
            "details/$coinId"

        override val screenName: String
            get() = "details/{$COIN_ID}"
    }
}