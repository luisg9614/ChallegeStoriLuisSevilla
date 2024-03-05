package com.example.challegestori.screens.sign_up

import androidx.compose.runtime.mutableStateOf
import com.example.challegestori.HOME_SCREEN
import com.example.challegestori.SIGN_UP_SCREEN
import com.example.challegestori.TAKE_PHOTO
import com.example.challegestori.common.ext.isValidEmail
import com.example.challegestori.common.ext.isValidPassword
import com.example.challegestori.common.ext.passwordMatches
import com.example.challegestori.common.snackbar.SnackbarManager
import com.example.challegestori.model.service.UserService
import com.example.challegestori.model.service.LogService
import com.example.challegestori.model.data.User
import com.example.challegestori.screens.ChallengeStoriViewModel
import com.example.challegestori.R.string as AppText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val accountService: UserService,
    logService: LogService
) : ChallengeStoriViewModel(logService) {
    var uiState = mutableStateOf(SignUpUiState())
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

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        if (!email.isValidEmail()) {
            SnackbarManager.showMessage(AppText.email_error)
            return
        }

        if (!password.isValidPassword()) {
            SnackbarManager.showMessage(AppText.password_error)
            return
        }

        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            SnackbarManager.showMessage(AppText.password_match_error)
            return
        }

        launchCatching {
            accountService.linkAccount(email, password)
            accountService.saveUserData(
                User(
                name = "Luis",
                lastName = "Sevilla",
                email = email)
            )
            openAndPopUp(HOME_SCREEN, SIGN_UP_SCREEN)
        }
    }

    fun onTakePhotoClick(openScreen: (String) -> Unit,) {
        launchCatching {
            openScreen(TAKE_PHOTO)
        }
    }
}