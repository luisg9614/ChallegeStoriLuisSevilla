package com.example.challegestori.screens.account

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.challegestori.ACCOUNT_ID
import com.example.challegestori.DETAIL_MOVEMENT_SCREEN
import com.example.challegestori.MOVEMENT_ID
import com.example.challegestori.common.ext.idFromParameter
import com.example.challegestori.model.data.Movement
import com.example.challegestori.model.service.AccountService
import com.example.challegestori.model.service.LogService
import com.example.challegestori.screens.ChallengeStoriViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    logService: LogService,
    accountService: AccountService
) : ChallengeStoriViewModel(logService) {

    val movements = mutableStateOf<List<Movement>?>(emptyList())

    init {
        val taskId = savedStateHandle.get<String>(ACCOUNT_ID)
        if (taskId != null) {
            launchCatching {
                movements.value = accountService.getMovements(taskId.idFromParameter())
            }
        }
    }

    fun onMovementClick(openScreen: (String) -> Unit, movement: Movement) {
        launchCatching {
            openScreen("$DETAIL_MOVEMENT_SCREEN?$MOVEMENT_ID=${Json.encodeToJsonElement(movement.detail)}")
        }
    }
}