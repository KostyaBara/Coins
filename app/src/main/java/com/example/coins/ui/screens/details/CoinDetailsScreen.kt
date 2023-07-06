package com.example.coins.ui.screens.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinDetailsScreen(
    coinId: String?,
    viewModel: CoinDetailsViewModel = viewModel(factory = CoinDetailsViewModel.factory(coinId)),
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val uiState = viewModel.uiState.collectAsState().value

    when (uiState) {
        is CoinDetailsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is CoinDetailsUiState.Success -> CoinDetailsCard(
            uiState.coin,
            onBackClick = onBackClick,
            coinId = uiState.coin.id,
            modifier = modifier
                .fillMaxSize()
        )

        is CoinDetailsUiState.Error -> ErrorScreen(modifier = modifier.fillMaxSize())
    }

//    Row {
//        Text(${ uiState.co })
//
//    }
}

@Composable
private fun NavBar(
    onClick: () -> Unit,
    coin: String?,
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
            text = ("$coin".capitalize()),
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
private fun CoinDetailsCard(
    coin: Coin,
    modifier: Modifier,
    onBackClick: () -> Unit,
    coinId: String?
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        NavBar(
            coin = coinId,
            onClick = onBackClick,
        )

        val list = listOf<Float>(
            4.33F,
            500F,
            5.32F,
            15.1F,
            82.6F,
            12F,
            55F,
            100F,
            60F,
            26F,
            13F,
            34F,
            55F,
            139F,
            35F,
            79F
        )

        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
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
                modifier = Modifier.size(48.dp, 48.dp)

            )

            Text("${coin.currentPrice}")
        }

        BarChart(values = list)
    }

}


@Composable
fun BarChart(
    modifier: Modifier = Modifier,
    values: List<Float>,
    maxHeight: Dp = 200.dp
) {
    assert(values.isNotEmpty()) { "Input values are empty" }

    val borderColor = Color.Gray
    val density = LocalDensity.current
    val strokeWidth = with(density) { 1.dp.toPx() }
val list = listOf<Float>(
    4.33F,
    500F,
    5.32F,
    15.1F,
    82.6F,
    12F,
    55F,
    100F,
    60F,
    26F,
    13F,
    34F,
    55F,
    139F,
    35F,
    79F
)

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
                color = Color.Black,
                maxHeight = maxHeight
            )
        }

    }
}

@Composable
private fun RowScope.Bar(
    value: Float,
    color: Color,
    maxHeight: Dp
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





