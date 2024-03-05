package com.example.challegestori.model.service.impl

import com.example.challegestori.model.data.Account
import com.example.challegestori.model.data.Movement
import com.example.challegestori.model.service.AccountService
import com.example.challegestori.model.service.UserService
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    private val auth: UserService,
    private val firestore: FirebaseFirestore
) : AccountService {

    private val collection get() = firestore.collection("users")
        .document(auth.currentUserId).collection("cuentas")
    private var movements: List<Movement> = emptyList()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val accounts: Flow<List<Account>>
        get() =
            auth.currentUser.flatMapLatest { user ->
                firestore
                    .collection("users")
                    .document(user.id)
                    .collection("cuentas")
                    .dataObjects()
            }

    override suspend fun getMovements(accountId: String): List<Movement> {
        movements = collection.document(accountId).collection("movimientos").get().await().toObjects()
        return movements
    }

    override fun getMovement(index: String): Movement? {
        return movements.find { movement: Movement -> movement.id == index }
    }


    override suspend fun save(account: Account): String {
        TODO("Not yet implemented")
    }

    override suspend fun update(account: Account) {
        TODO("Not yet implemented")
    }

    override suspend fun delete(accountId: String) {
        TODO("Not yet implemented")
    }
}