package com.example.challegestori

import android.content.res.Resources
import android.os.Bundle
import androidx.compose.material.*
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.challegestori.common.snackbar.SnackbarManager
import com.example.challegestori.model.data.MovementDetail
import com.example.challegestori.screens.account.AccountScreen
import com.example.challegestori.screens.detail.MovementDetailScreen
import com.example.challegestori.screens.home.HomeScreen
import com.example.challegestori.screens.image_picker.BetterModalBottomSheet
import com.example.challegestori.screens.login.LoginScreen
import com.example.challegestori.screens.sign_up.SignUpScreen
import com.example.challegestori.ui.theme.ChallegeStoriTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json

@Composable
fun MakeChallengeStoriMainView() {
    ChallegeStoriTheme {

        Surface {
            val appState = rememberAppState()

            Scaffold(
                snackbarHost = {
                    SnackbarHost(
                        hostState = it,
                        modifier = Modifier.padding(8.dp),
                        snackbar = { snackbarData ->
                            Snackbar(snackbarData)
                        }
                    )
                },
                scaffoldState = appState.scaffoldState
            ) { innerPaddingModifier ->
                NavHost(
                    navController = appState.navController,
                    startDestination = LOGIN_SCREEN,
                    modifier = Modifier.padding(innerPaddingModifier)
                ) {
                    makeItSoGraph(appState)
                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    navController: NavHostController = rememberNavController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) =
    remember(scaffoldState, navController, snackbarManager, resources, coroutineScope) {
        ChallengeStoriState(scaffoldState, navController, snackbarManager, resources, coroutineScope)
    }

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.makeItSoGraph(appState: ChallengeStoriState) {
    composable(LOGIN_SCREEN) {
        LoginScreen(openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) })
    }

    composable(
        route = SIGN_UP_SCREEN
    ) {
        val data = it.savedStateHandle.getLiveData<String>("key").value
        SignUpScreen(imagePath = data.orEmpty(), openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }, openScreen = { route -> appState.navigate(route) })
    }

    composable(HOME_SCREEN) {
        HomeScreen(openScreen = { route -> appState.navigate(route) })
    }

    composable(
        route = "$ACCOUNT_SCREEN$ACCOUNT_ID_ARG",
        arguments = listOf(navArgument(ACCOUNT_ID) {
            nullable = true
            defaultValue = null
        })
    ) {
        AccountScreen(
            openScreen = { route -> appState.navigate(route) }
        )
    }

    composable(
        route = "$DETAIL_MOVEMENT_SCREEN$MOVEMENT_ID_ARG",
        arguments = listOf(navArgument(MOVEMENT_ID) {
            type = detailNavType
        })
    ) {
        MovementDetailScreen()
    }

    composable(TAKE_PHOTO) {
        BetterModalBottomSheet(showSheet = true,onDismissRequest = { appState.popUp() } , onResult = { data -> appState.popUpArgument(data) })
    }

}

val detailNavType = object : NavType<MovementDetail>(isNullableAllowed = false){
    override fun get(bundle: Bundle, key: String): MovementDetail? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): MovementDetail {
        return Json.decodeFromString(value)
    }

    override fun put(bundle: Bundle, key: String, value: MovementDetail) {
        bundle.putParcelable(key, value)
    }

}
