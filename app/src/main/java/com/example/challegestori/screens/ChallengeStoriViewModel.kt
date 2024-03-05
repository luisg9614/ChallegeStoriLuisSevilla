package com.example.challegestori.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challegestori.common.snackbar.SnackbarManager
import com.example.challegestori.common.snackbar.SnackbarMessage.Companion.toSnackbarMessage
import com.example.challegestori.model.service.LogService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class ChallengeStoriViewModel(private val logService: LogService) : ViewModel() {
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
                logService.logNonFatalCrash(throwable)
            },
            block = block
        )
}