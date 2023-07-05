package com.example.coins.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coins.R
import com.example.coins.ui.screens.NavDestinations.*
import com.example.coins.ui.screens.details.CoinDetailsScreen
import com.example.coins.ui.screens.list.CoinsListScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RootScreen(
    navController: NavHostController = rememberNavController(),
) {
    Scaffold(
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = CoinsList.screenName,
            modifier = Modifier.padding(innerPadding),
        ) {
            composable(route = CoinsList.screenName) {
                CoinsListScreen(
                    onItemClick = {
                        navController.navigate(CoinDetails.withId(it.id))
                    },
                )
            }
            composable(route = CoinDetails.screenName) { backStackEntry ->
                CoinDetailsScreen(
                    coinId = CoinDetails.getCoinId(backStackEntry),
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinsTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.assets),
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(12.dp)
                )
                Image(
                    painter = (painterResource(R.drawable.equalizer)),
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                )
            }
        },
        modifier = modifier
    )
}