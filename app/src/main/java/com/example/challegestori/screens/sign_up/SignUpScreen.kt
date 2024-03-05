package com.example.challegestori.screens.sign_up

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.challegestori.common.composable.BasicButton
import com.example.challegestori.common.composable.BasicToolbar
import com.example.challegestori.common.composable.EmailField
import com.example.challegestori.common.composable.PasswordField
import com.example.challegestori.common.composable.RepeatPasswordField
import com.example.challegestori.common.ext.basicButton
import com.example.challegestori.common.ext.fieldModifier
import com.example.challegestori.ui.theme.ChallegeStoriTheme
import com.example.challegestori.R.string as AppText

@Composable
fun SignUpScreen(
    imagePath: String,
    openAndPopUp: (String, String) -> Unit,
    openScreen: (String) -> Unit,
    viewModel: SignUpViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState

    SignUpScreenContent(
        imagePath = imagePath,
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = { viewModel.onSignUpClick(openAndPopUp) },
        onTakePhotoClick = { viewModel.onTakePhotoClick(openScreen) }
    )
}

@Composable
fun SignUpScreenContent(
    imagePath: String,
    modifier: Modifier = Modifier,
    uiState: SignUpUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit,
    onTakePhotoClick: () -> Unit
) {
    val fieldModifier = Modifier.fieldModifier()

    BasicToolbar(AppText.create_account)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailField(uiState.email, onEmailChange, fieldModifier)
        PasswordField(uiState.password, onPasswordChange, fieldModifier)
        RepeatPasswordField(uiState.repeatPassword, onRepeatPasswordChange, fieldModifier)
        if (imagePath.isNotEmpty()) {
            AsyncImage(
                model = imagePath,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.height(120.dp).width(120.dp),
                contentDescription = "Selected image",
            )
        }
        BasicButton(AppText.take_photo, Modifier.basicButton()) {
            onTakePhotoClick()
        }
        BasicButton(AppText.create_account, Modifier.basicButton()) {
            onSignUpClick()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    val uiState = SignUpUiState(
        email = "email@test.com"
    )

    ChallegeStoriTheme {
        SignUpScreenContent(
            imagePath = "",
            uiState = uiState,
            onEmailChange = { },
            onPasswordChange = { },
            onRepeatPasswordChange = { },
            onSignUpClick = { },
            onTakePhotoClick = {}
        )
    }
}