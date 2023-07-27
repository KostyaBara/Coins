package com.example.coins.ui.screens.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coins.R
import com.example.coins.data.model.Coin
import com.example.coins.ui.helpers.CoinCard
import com.example.coins.ui.helpers.CoinsTopAppBar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FavoritesScreen(
    onItemClick: (Coin) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState().value
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val isLoading = uiState is FavoritesUiState.Loading
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { viewModel.refresh() }
    )

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .pullRefresh(pullRefreshState),
        topBar = { CoinsTopAppBar(scrollBehavior = scrollBehavior) },
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when (uiState) {
                is FavoritesUiState.Loading -> {}
                is FavoritesUiState.Success -> {
                    SuccessFavoritesScreen(
                        coins = uiState.coins,
                        onItemClick = onItemClick,
                        modifier = modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    )
                }

                is FavoritesUiState.Error -> ErrorFavoritesScreen(modifier = modifier.fillMaxSize())
                is FavoritesUiState.Empty -> EmptyScreen(modifier = Modifier)
            }

            PullRefreshIndicator(
                refreshing = isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.Center)
            )
        }
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
    ) {
        itemsIndexed(
            coins,
            key = { _,coin -> coin.id }
        ) { index, coin ->
            CoinCard(
                coin = coin,
                isLast = index == coins.lastIndex,
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


