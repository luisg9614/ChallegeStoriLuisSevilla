package com.example.challegestori.model.service

import com.example.challegestori.model.data.Account
import com.example.challegestori.model.data.Movement
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val accounts: Flow<List<Account>>
    suspend fun getMovements(accountId: String): List<Movement>
    fun getMovement(index: String): Movement?
    suspend fun save(account: Account): String
    suspend fun update(account: Account)
    suspend fun delete(accountId: String)
}