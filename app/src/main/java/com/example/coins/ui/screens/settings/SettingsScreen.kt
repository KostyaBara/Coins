package com.example.coins.ui.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.coins.R
import com.example.coins.data.model.MenuItem
import com.example.coins.ui.screens.favorites.FavoritesViewModel
import com.example.coins.ui.theme.CoinsTheme

enum class AppTheme {
    Light, Dark, System
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(
    viewModel: FavoritesViewModel = viewModel(factory = FavoritesViewModel.Factory),
    modeChangeOptions: List<MenuItem.ButtonItem>,
    modifier: Modifier = Modifier,
) {

    var selectedItemName by rememberSaveable { mutableStateOf(viewModel.colorScheme) }

    Column(modifier = modifier) {

        NavBar()

        Spacer(modifier = Modifier.height(8.dp))

        CoinsTheme(
            darkTheme = when (selectedItemName) {
                "Light mode" -> true
                "Dark mode" -> false
                else -> isSystemInDarkTheme()
            }
        ) {
            modeChangeOptions.forEach { item ->
                val onCLick = {
                    viewModel.colorScheme = item.name
                    selectedItemName = item.name
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

        Image(
            modifier = modifier.size(20.dp),
            painter = painterResource(R.drawable.anadi),
            contentDescription = stringResource(R.string.loading)
        )
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

@Composable
fun LabeledRadioButton(
    selected: Boolean,
    onClick: () -> Unit,
    label: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(label)
    }
}