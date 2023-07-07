package com.example.coins.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.coins.R
import com.example.coins.data.model.Coin
import com.example.coins.ui.screens.list.ErrorScreen
import com.example.coins.ui.screens.list.LoadingScreen
import com.example.coins.ui.theme.CoinsTheme
import com.example.coins.utils.currentPriceFormat
import com.example.coins.utils.priceChangeFormat
import com.example.coins.utils.priceChangePercentageFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailsScreen(
    coinId: String?,
    viewModel: CoinDetailsViewModel = viewModel(factory = CoinDetailsViewModel.factory(coinId)),
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val uiState = viewModel.uiState.collectAsState().value

    when (uiState) {
        is CoinDetailsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CoinDetailsUiState.Success -> SuccessScreen(
            uiState = uiState,
            onBackClick = onBackClick,
        )

        is CoinDetailsUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }
}

@Composable
private fun NavBar(
    onClick: () -> Unit,
    coinName: String?,
) {

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = null,
            tint = Color.Black,
            modifier = Modifier
                .clickable { onClick() }
                .align(alignment = Alignment.CenterVertically)
                .padding(12.dp)
        )

        Spacer(modifier = Modifier.height(52.dp))

        Text(
            text = ("$coinName".capitalize()),
            textAlign = TextAlign.Start,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 80.dp)
        )
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
private fun SuccessScreen(
    uiState: CoinDetailsUiState.Success,
    onBackClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
    ) {
        NavBar(
            coinName = uiState.coin.name,
            onClick = onBackClick,
        )

        Spacer(modifier = Modifier.height(24.dp))

        CoinInfoBlock(coin = uiState.coin)

        PriceChangeInfoBlock(coin = uiState.coin)

        Spacer(modifier = Modifier.weight(1f))

        BarChart(values = uiState.chart)
    }
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
                .size(60.dp, 60.dp)
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
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 12.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

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
fun BarChart(
    modifier: Modifier = Modifier,
    values: List<Float>,
    maxHeight: Dp = 200.dp,
) {
    assert(values.isNotEmpty()) { "Input values are empty" }

    val borderColor = Color.Gray
    val density = LocalDensity.current
    val strokeWidth = with(density) { 1.dp.toPx() }

    Row(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .height(maxHeight)
                .drawBehind {
                    // draw X-Axis
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth
                    )
                    // draw Y-Axis
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = strokeWidth
                    )
                }
        ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        values.forEach { item ->
            Bar(
                value = item,
                color = Color(86, green = 160, blue = 241),
                maxHeight = maxHeight
            )
        }
    }
}

@Composable
private fun RowScope.Bar(
    value: Float,
    color: Color,
    maxHeight: Dp,
) {
    val itemHeight = remember(value) { value * maxHeight.value / 100 }

    Spacer(
        modifier = Modifier
            .padding(horizontal = 3.dp)
            .height(itemHeight.dp)
            .weight(1f)
            .background(color)
    )
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
                ),
            )
        }
    }
}