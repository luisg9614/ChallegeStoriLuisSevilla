package com.example.challegestori.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.challegestori.common.composable.BasicButton
import com.example.challegestori.common.composable.BasicToolbar
import com.example.challegestori.common.composable.EmailField
import com.example.challegestori.common.composable.PasswordField
import com.example.challegestori.common.ext.basicButton
import com.example.challegestori.common.ext.fieldModifier
import com.example.challegestori.R.string as AppText

@Composable
fun LoginScreen(
    openAndPopUp: (String, String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    LoginScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onSignInClick = { viewModel.onSignInClick(openAndPopUp) },
        onRegisterClick = { viewModel.onRegisterClick(openAndPopUp) },
    )
}

@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignInClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
    BasicToolbar(AppText.login_details)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, onEmailChange, Modifier.fieldModifier())
        PasswordField(uiState.password, onPasswordChange, Modifier.fieldModifier())
        BasicButton(AppText.sign_in, Modifier.basicButton()) { onSignInClick() }
        BasicButton(AppText.register, Modifier.basicButton()) { onRegisterClick() }
    }
}