package com.example.coins.ui.screens.details

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailsScreen(
    coinId: String?,
    viewModel: CoinDetailsViewModel = viewModel(factory = CoinDetailsViewModel.factory(coinId)),
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = viewModel.uiState.collectAsState().value
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { CoinDetailsTopNavBar(scrollBehavior = scrollBehavior, onBackClick = onBackClick) },
    ) { innerPadding ->
        CoinCard(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun CoinCard(
    modifier: Modifier = Modifier,

    ) {
Text("Be water, my friend")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailsTopNavBar(
    onBackClick: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        scrollBehavior = scrollBehavior,
        title = {
            Row {
                IconButton(
                    onClick = { onBackClick() },
                    modifier = Modifier
                        .size(40.dp)
                        .align(alignment = Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.width(20.dp))

                Text(
                    text = ("Coin"),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                )
            }
        }
    )
}







