package com.example.challegestori.model.service

import com.example.challegestori.model.data.User
import kotlinx.coroutines.flow.Flow

interface UserService {
    val currentUserId: String
    val hasUser: Boolean

    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String)
    suspend fun sendRecoveryEmail(email: String)
    suspend fun linkAccount(email: String, password: String)
    suspend fun saveUserData(user: User)
}