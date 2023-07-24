package com.example.coins.ui.screens.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coins.R
import com.example.coins.data.model.Coin
import com.example.coins.ui.helpers.CoinsTopAppBar
import com.example.coins.utils.currentPriceFormat
import com.example.coins.utils.priceChangeFormat
import com.example.coins.utils.priceChangePercentageFormat

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CoinsListScreen(
    onItemClick: (Coin) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CoinsListViewModel = viewModel(factory = CoinsListViewModel.Factory),
) {
    val uiState = viewModel.uiState.collectAsState().value
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

//    val refreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()
//
//    val pullRefreshState = rememberPullRefreshState(
//        refreshing = refreshing,
//        onRefresh = { viewModel.refresh() }
//    )
//    val coinData: List<Coin>? by viewModel.item.collectAsStateWithLifecycle()


//    Box(
//        Modifier
//            .pullRefresh(pullRefreshState)
//            .verticalScroll(rememberScrollState())
//    ) {

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { CoinsTopAppBar(scrollBehavior = scrollBehavior) },
    ) { paddingValues ->

        when (uiState) {
            is CoinsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is CoinsUiState.Success -> CoinsSuccessScreen(
                uiState.coins,
                onItemClick = onItemClick,
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            )

                is CoinsUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
            }
        }

//        PullRefreshIndicator(
//            refreshing = refreshing,
//            state = pullRefreshState,
//            modifier = Modifier.align(Alignment.TopCenter)
//        )
    }


@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
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
fun CoinCard(
    coin: Coin,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(coin.image)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                .clip(shape = RoundedCornerShape(16.dp))
        )

        Spacer(modifier = Modifier.width(12.dp))

        NamePriceCard(coin)

        Spacer(modifier = Modifier.weight(1f))

        PriceChangeCard(coin)

        IconButton(
            onClick = { onClick() },
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = null,
                tint = Color.Gray
            )
        }
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
        Row() {
            Image(
                painter = if (coin.priceChangePercentage < 0) {
                    painterResource(R.drawable.red_arrow_down)
                } else if (coin.priceChangePercentage > 0) {
                    painterResource(R.drawable.green_arrow_up)
                } else painterResource(R.drawable.dash),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = coin.priceChangePercentage.priceChangePercentageFormat(),
                fontSize = 15.sp
            )
        }
    }
}

@Composable
private fun CoinCardPreview() {

}