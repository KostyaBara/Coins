package com.example.coins.ui.screens.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coins.R
import com.example.coins.data.model.Coin
import com.example.coins.ui.helpers.CoinsTopAppBar
import com.example.coins.ui.screens.list.CoinCard
import com.example.coins.ui.screens.list.LoadingScreen

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FavoritesScreen(
    onItemClick: (Coin) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory),
) {
    val uiState = viewModel.uiState.collectAsState().value
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    viewModel.isOnFavoriteScreen = true

    val refreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

    val pullRefreshState = rememberPullRefreshState(
        refreshing,
        onRefresh = { viewModel.refresh() }
    )
    val coinData: List<Coin>? by viewModel.item.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .pullRefresh(pullRefreshState),
        topBar = { CoinsTopAppBar(scrollBehavior = scrollBehavior) },
    ) { paddingValues ->

        when (uiState) {
            is FavoritesUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is FavoritesUiState.Success -> {
                if (coinData == null) {} else {
                    SuccessFavoritesScreen(
                        coins = uiState.coins,
                        onItemClick = onItemClick,
                        modifier = modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    )
                }
            }


            is FavoritesUiState.Error -> ErrorFavoritesScreen(modifier = modifier.fillMaxSize())
            is FavoritesUiState.Empty -> EmptyScreen(modifier = Modifier)
        }

        PullRefreshIndicator(
            refreshing,
            pullRefreshState,
            Modifier.padding(start = 168.dp)
        )
    }
}


@Composable
private fun SuccessFavoritesScreen(
    coins: List<Coin>,
    onItemClick: (Coin) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(
            coins,
            key = { coin -> coin.id }
        ) { coin ->
            CoinCard(
                coin = coin,
                onClick = { onItemClick(coin) }
            )
        }
    }
}

@Composable
private fun ErrorFavoritesScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun EmptyScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .background(Color.Yellow)
            .fillMaxSize()
    ) {

        Text(
            text = "No favorites yet!",
            fontSize = 50.sp,
            textAlign = TextAlign.Center
        )
    }
}


