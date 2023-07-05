package com.example.coins.ui.screens.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    val list = listOf<Float>(4.33F, 5.32F, 15.1F)
    val uiState = viewModel.uiState.collectAsState().value

    NavBar(
        coin = coinId,
        onClick = { onBackClick() },
    )

}

//    @OptIn(ExperimentalMaterial3Api::class)
//    @Composable
//    fun CoinDetailsTopNavBar(
//        onBackClick: () -> Unit,
//        scrollBehavior: TopAppBarScrollBehavior,
//        modifier: Modifier = Modifier,
//        coin: String?,
//    ) {
//        CenterAlignedTopAppBar(
//            scrollBehavior = scrollBehavior,
//            title = {
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Start,
//                    modifier = Modifier
//                        .padding(30.dp)
//                        .fillMaxWidth()
//
//                ) {
//                    IconButton(
//                        onClick = { onBackClick() },
//                        modifier = Modifier
//                            .size(48.dp)
//                    ) {
//                        Icon(
//                            imageVector = Icons.Filled.ArrowBack,
//                            contentDescription = null,
//                            tint = Color.Black,
//                        )
//                    }
//
//                    Spacer(modifier = Modifier.width(56.dp))
//
//                    Text(
//                        text = ("$coin".capitalize()),
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier
//                    )
//                }
//            }
//        )
//    }

    @Composable
    fun NavBar(
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



//@Composable
//internal fun BarChart(
//    modifier: Modifier = Modifier,
//    values: List<Float>,
//    maxHeight: Dp = 200.dp
//) {
//    assert(values.isNotEmpty()) { "Input values are empty" }
//
//    val borderColor = Color.Gray
//    val density = LocalDensity.current
//    val strokeWidth = with(density) { 1.dp.toPx() }
//
//    Row(
//        modifier = modifier.then(
//            Modifier
//                .fillMaxWidth()
//                .height(maxHeight)
//                .drawBehind {
//                    // draw X-Axis
//                    drawLine(
//                        color = borderColor,
//                        start = Offset(0f, size.height),
//                        end = Offset(size.width, size.height),
//                        strokeWidth = strokeWidth
//                    )
//                    // draw Y-Axis
//                    drawLine(
//                        color = borderColor,
//                        start = Offset(0f, 0f),
//                        end = Offset(0f, size.height),
//                        strokeWidth = strokeWidth
//                    )
//                }
//        ),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.Bottom
//    ) {
//        values.forEach { item ->
//            Bar(
//                value = item,
//                color = Color.Black,
//                maxHeight = maxHeight
//            )
//        }
//
//    }
//}






