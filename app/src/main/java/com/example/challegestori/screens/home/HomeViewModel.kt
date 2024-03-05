package com.example.challegestori.screens.home

import com.example.challegestori.ACCOUNT_SCREEN
import com.example.challegestori.ACCOUNT_ID
import com.example.challegestori.model.data.Account
import com.example.challegestori.model.service.AccountService
import com.example.challegestori.model.service.LogService
import com.example.challegestori.screens.ChallengeStoriViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    logService: LogService,
    accountService: AccountService
) : ChallengeStoriViewModel(logService) {
    val accounts = accountService.accounts

    fun onAccountClick(openScreen: (String) -> Unit,account: Account) {
        launchCatching {
            openScreen("$ACCOUNT_SCREEN?$ACCOUNT_ID={${account.id}}")
        }
    }
}