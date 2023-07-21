package com.example.coins.ui.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.example.coins.R
import com.example.coins.data.model.MenuItem

@Composable
fun SettingsScreen(
    modeChangeOptions: List<MenuItem.ButtonItem>,
    modifier: Modifier = Modifier,
//    onSelectionChanged: (MenuItem) -> Unit,
) {

    var selectedItemName by rememberSaveable { mutableStateOf("") }

    Column(modifier = modifier) {

        NavBar(
        )
//
        modeChangeOptions.forEach { item ->
            val onCLick = {
                selectedItemName = item.name
            }

            MenuItemRow(
                item = item,
                selectedItemName = selectedItemName,
                onClick = onCLick,
                modifier = Modifier.selectable(
                    selected = selectedItemName == item.name,
                    onClick = onCLick
                )
            )
        }

//        RadioButton(selected = true, onClick = { println("Trans") })

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
            modifier = Modifier.padding(end = 32.dp, start = 48.dp
            )
        )
    }
}

@Composable
fun MenuItemRow(
    item: MenuItem,
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
            onClick = onClick
        )
        Text(text = item.name,
        color = item.color)
    }
}