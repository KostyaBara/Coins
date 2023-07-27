package com.example.coins.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coins.R
import com.example.coins.data.model.Coin
import com.example.coins.ui.chart.ChartData
import com.example.coins.ui.chart.LineChart
import com.example.coins.ui.theme.CoinsTheme
import com.example.coins.utils.currentPriceFormat
import com.example.coins.utils.priceChangeFormat
import com.example.coins.utils.priceChangePercentageFormat

@Composable
fun CoinDetailsScreen(
    coinId: String?,
    viewModel: CoinDetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    coinId ?: return Box {}

    val lifecycleOwner = LocalLifecycleOwner.current
    val uiState = viewModel.uiState.collectAsState().value

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                viewModel.onCreate(coinId)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    when (uiState) {
        is CoinDetailsUiState.Loading -> LoadingScreen(modifier = Modifier.fillMaxSize())
        is CoinDetailsUiState.Success -> SuccessScreen(
            uiState = uiState,
            onBackClick = onBackClick,
            onFavorite = { isFavorite ->
                viewModel.setFavorite(isFavorite)
            }
        )

        is CoinDetailsUiState.Error -> ErrorScreen(modifier = Modifier.fillMaxSize())
    }
}

@Composable
private fun NavBar(
    uiState: CoinDetailsUiState.Success,
    onBackClick: () -> Unit,
    onFavorite: (Boolean) -> Unit,
) {

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable { onBackClick() }
                .align(alignment = Alignment.CenterVertically)
        )

        Spacer(modifier = Modifier.height(52.dp))

        Text(
            text = uiState.coin.name,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 32.dp)
        )

        val icon =
            if (uiState.coin.isFavorite) R.drawable.baseline_favorite_24 else R.drawable.heart_icon_outlined

        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = Color.Red,
            modifier = Modifier
                .clickable { onFavorite(!uiState.coin.isFavorite) }
                .align(alignment = Alignment.CenterVertically)
                .size(36.dp)
        )
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
private fun SuccessScreen(
    uiState: CoinDetailsUiState.Success,
    onBackClick: () -> Unit,
    onFavorite: (Boolean) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {

        NavBar(
            uiState = uiState,
            onBackClick = onBackClick,
            onFavorite = onFavorite
        )

        Spacer(modifier = Modifier.height(24.dp))

        CoinInfoBlock(coin = uiState.coin)

        PriceChangeInfoBlock(coin = uiState.coin)

        Spacer(modifier = Modifier.height(36.dp))

        LineChart(
            chartData = uiState.chartData,
            chartLineEnabled = true,

            chartOnFullVerticalSpace = true,
//                onValueSelected = onChartValueSelected,
//                onDragEnd = onChartDragEnd,
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        )
        Divider()
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

@Composable
private fun CoinInfoBlock(
    coin: Coin,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {

        Text(
            text = coin.currentPrice.currentPriceFormat(),
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(12.dp)

        )

        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(coin.image)
                .crossfade(true)
                .build(),
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.app_name),
//                contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(60.dp)
                .padding(end = 20.dp)
        )
    }
}

@Composable
fun PriceChangeInfoBlock(coin: Coin) {

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = coin.priceChange.priceChangeFormat(),
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 12.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
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

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = coin.priceChangePercentage.priceChangePercentageFormat(),
                fontSize = 24.sp
            )
        }
    }
}

@Composable
@Preview
private fun SuccessScreenPreview() {
    CoinsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            SuccessScreen(
                uiState = CoinDetailsUiState.Success(
                    coin = Coin(
                        name = "Bitcoin",
                        image = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
                        currentPrice = 31999.0,
                    ),
                    chartData = ChartData()
                ),
                onBackClick = {},
                onFavorite = {}
            )
        }
    }
}