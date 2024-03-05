package com.example.challegestori.screens.login

import androidx.compose.runtime.mutableStateOf
import com.example.challegestori.HOME_SCREEN
import com.example.challegestori.LOGIN_SCREEN
import com.example.challegestori.SIGN_UP_SCREEN
import com.example.challegestori.R.string as AppText
import com.example.challegestori.common.ext.isValidEmail
import com.example.challegestori.common.snackbar.SnackbarManager
import com.example.challegestori.model.service.UserService
import com.example.challegestori.model.service.LogService
import com.example.challegestori.screens.ChallengeStoriViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: UserService,
    logService: LogService
): ChallengeStoriViewModel(logService) {
    var uiState = mutableStateOf(LoginUiState())
        private set

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (password.isBlank()) {
            SnackbarManager.showMessage(AppText.empty_password_error)
            return
        }

        launchCatching {
            accountService.authenticate(email, password)
            openAndPopUp(HOME_SCREEN, LOGIN_SCREEN)
        }
    }

    fun onRegisterClick(openAndPopUp: (String, String) -> Unit) {
            openAndPopUp(SIGN_UP_SCREEN, LOGIN_SCREEN)
    }
}