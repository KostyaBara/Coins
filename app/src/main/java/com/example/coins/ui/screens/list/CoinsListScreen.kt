package com.example.coins.ui.screens.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coins.R
import com.example.coins.data.LoadingMode
import com.example.coins.data.model.Coin
import com.example.coins.ui.helpers.CoinCard
import com.example.coins.ui.helpers.CoinsTopAppBar
import com.example.coins.utils.currentPriceFormat
import com.example.coins.utils.priceChangeFormat
import com.example.coins.utils.priceChangePercentageFormat

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CoinsListScreen(
    onItemClick: (Coin) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CoinsListViewModel = hiltViewModel(),
) {
    val uiState = viewModel.uiState.collectAsState().value ?: return Box {}
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    val isLoading = uiState is CoinsUiState.Loading
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = { viewModel.getCoins(loadingMode = LoadingMode.NET) }
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
                is CoinsUiState.Loading -> {}
                is CoinsUiState.Success -> CoinsSuccessScreen(
                    uiState.coins,
                    onItemClick = onItemClick,
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )

                is CoinsUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
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
fun ResultScreen(
    coins: String,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Text(text = coins)
    }
}

@Composable
fun CoinsSuccessScreen(
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
private fun ErrorScreen(modifier: Modifier = Modifier) {
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
fun NamePriceCard(coin: Coin) {
    Column(
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = coin.name,
            fontSize = 20.sp
        )
        Text(
            text = coin.currentPrice.currentPriceFormat(),
            fontSize = 16.sp
        )
    }
}

@Composable
fun PriceChangeCard(coin: Coin) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Text(
            text = coin.priceChange.priceChangeFormat(),
            fontSize = 18.sp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            when {
                coin.priceChangePercentage < 0 -> R.drawable.red_arrow_down
                coin.priceChangePercentage > 0 -> R.drawable.green_arrow_up
                else -> null
            }?.let { resId ->
                Image(
                    painter = painterResource(resId),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = coin.priceChangePercentage.priceChangePercentageFormat(),
                fontSize = 15.sp,
                textAlign = TextAlign.End,
                modifier = Modifier.width(54.dp)
            )
        }
    }
}

@Composable
private fun CoinCardPreview() {

}