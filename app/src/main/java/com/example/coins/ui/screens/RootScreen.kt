package com.example.coins.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coins.ui.screens.NavDestinations.CoinDetails
import com.example.coins.ui.screens.NavDestinations.Home
import com.example.coins.ui.screens.details.CoinDetailsScreen

@Composable
fun RootScreen(
    navController: NavHostController = rememberNavController(),
) {

    Scaffold(
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Home.screenName,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(route = Home.screenName) {
                HomeScreen(
                    onItemClick = {
                        navController.navigate(CoinDetails.withId(it.id))
                    },
                )
            }
            composable(route = CoinDetails.screenName) { backStackEntry ->
                CoinDetailsScreen(
                    coinId = CoinDetails.getCoinId(backStackEntry),
                    onBackClick = { navController.popBackStack() },
                )
            }
        }
    }
}

