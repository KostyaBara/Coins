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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.coins.data.model.ThemeType

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    modifier: Modifier= Modifier,
) {
    val uiState = viewModel.uiState.collectAsState().value

    Column(
        modifier = modifier
    ) {

        NavBar()

        Spacer(modifier = Modifier.height(8.dp))

        MenuItemRow(
            themeType = ThemeType.LIGHT,
            isSelected = !uiState.isDarkTheme,
            onClick = { viewModel.selectTheme(ThemeType.LIGHT) },
        )
        Spacer(modifier = Modifier.height(8.dp))
        MenuItemRow(
            themeType = ThemeType.DARK,
            isSelected = uiState.isDarkTheme,
            onClick = { viewModel.selectTheme(ThemeType.DARK) },
        )
    }
}

@Composable
private fun NavBar() {
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
    themeType: ThemeType,
    isSelected: Boolean,
    onClick: () -> Unit,
) {

    Row(
        modifier = Modifier.selectable(
            selected = isSelected,
            onClick = onClick
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        RadioButton(
            selected = isSelected,
            onClick = onClick,
        )
        Text(
            text = themeType.title,
            color = themeType.color
        )
    }
}
