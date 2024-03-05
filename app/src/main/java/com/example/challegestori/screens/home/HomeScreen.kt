package com.example.challegestori.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.challegestori.R
import com.example.challegestori.common.composable.BasicToolbar
import com.example.challegestori.model.data.Account
import com.google.firebase.firestore.DocumentId

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    openScreen: (String) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val tasks = viewModel.accounts.collectAsStateWithLifecycle(emptyList())
    BasicToolbar(R.string.home_screen)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Tus Cuentas:",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h5,
        )

        AccountList(
            tasks.value,
            onAccountClick = viewModel::onAccountClick,
            openScreen = openScreen
        )
    }
}

@Composable
fun AccountList(
    accounts: List<Account>,
    onAccountClick: ((String) -> Unit, Account) -> Unit,
    openScreen: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(accounts) { account ->
            AccountItem(account = account, onAccountClick = onAccountClick, openScreen = openScreen)
            Divider()
        }
    }
}

@Composable
fun AccountItem(
    account: Account,
    onAccountClick: ((String) -> Unit, Account) -> Unit,
    openScreen: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .clickable { onAccountClick(openScreen, account) }
            .padding(vertical = 8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Cuenta: ${account.number}", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = "Saldo: ${account.balance}")
    }
}
