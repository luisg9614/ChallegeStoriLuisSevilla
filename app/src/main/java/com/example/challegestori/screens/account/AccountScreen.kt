package com.example.challegestori.screens.account

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.challegestori.R
import com.example.challegestori.common.composable.BasicToolbar
import com.example.challegestori.model.data.Movement

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AccountScreen(
    openScreen: (String) -> Unit,
    viewModel: AccountViewModel = hiltViewModel()
) {
    val uiState by viewModel.movements

    BasicToolbar(R.string.account_screen)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Movimientos:",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h5,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (!uiState.isNullOrEmpty()) {
            MovementList(uiState!!, viewModel::onMovementClick, openScreen)
        }

    }

}

@Composable
fun MovementList(
    movements: List<Movement>,
    onMovementClick: ((String) -> Unit, Movement) -> Unit,
    openScreen: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(movements) { movement ->
            MovementItem(movement = movement, onMovementClick, openScreen)
            Divider()
        }
    }
}

@Composable
fun MovementItem(
    movement: Movement,
    onMovementClick: ((String) -> Unit, Movement) -> Unit,
    openScreen: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onMovementClick(openScreen, movement) }
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Fecha: ${movement.date}")
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Descripción: ${movement.description}")
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Monto: ${movement.amount}")
    }
}
