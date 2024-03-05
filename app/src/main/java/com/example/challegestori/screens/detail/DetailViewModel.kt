package com.example.challegestori.screens.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import com.example.challegestori.MOVEMENT_ID
import com.example.challegestori.model.data.MovementDetail
import com.example.challegestori.model.service.LogService
import com.example.challegestori.screens.ChallengeStoriViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    logService: LogService,
) : ChallengeStoriViewModel(logService) {
    val movementDetail = mutableStateOf<MovementDetail?>(null)

    init {
        val movementId: MovementDetail = savedStateHandle[MOVEMENT_ID]!!
        movementDetail.value = movementId
    }

}