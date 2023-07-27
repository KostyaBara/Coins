package com.example.coins.ui.screens.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coins.data.model.DataSource
import com.example.coins.data.model.MenuItem
import com.example.coins.ui.theme.CoinsTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    CoinsThemeApp(
        viewModel = viewModel,
        selectTheme = viewModel::selectTheme,
        modeChangeOptions = DataSource.modeChangeButtons,
        modifier = Modifier
    )
}

@Composable
fun CoinsThemeApp(
    viewModel: SettingsViewModel,
    selectTheme: (Boolean) -> Unit,
    modeChangeOptions: List<MenuItem.ButtonItem>,
    modifier: Modifier = Modifier,
) {

    var selectedItemName by rememberSaveable { mutableStateOf("Dark theme") }

    val uiState = viewModel.uiState.collectAsState().value

    if (!uiState.isDarkTheme) selectedItemName = "Light theme"


    Column(modifier = modifier) {

        NavBar()

        Spacer(modifier = Modifier.height(8.dp))

        CoinsTheme(darkTheme = uiState.isDarkTheme) {
            modeChangeOptions.forEach { item ->
                val onCLick = {
                    selectedItemName = item.name
                    if (item.name == "Light theme")
                        selectTheme(true)
                    else selectTheme(false)
                }

                MenuItemRow(
                    item = item,
                    selectedItemName = selectedItemName,
                    onClick = onCLick,
                    modifier = Modifier.selectable(
                        selected = selectedItemName == item.name,
                        onClick = onCLick
                    ),
                )
            }
        }

//        Image(
//            modifier = modifier.size(20.dp),
//            painter = painterResource(R.drawable.anadi),
//            contentDescription = stringResource(R.string.loading)
//        )
    }
}

@Composable
private fun NavBar(
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp)
    ) {

        Text(
            text = "Settings",
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                end = 32.dp, start = 48.dp
            )
        )
    }
}

@Composable
fun MenuItemRow(
    item: MenuItem.ButtonItem,
    selectedItemName: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        RadioButton(
            selected = selectedItemName == item.name,
            onClick = onClick,
        )
        Text(
            text = item.name,
            color = item.color
        )
    }
}
