package com.example.challegestori.model.data

import com.google.firebase.firestore.DocumentId

data class Account(
    @DocumentId val id: String = "",
    val name: String= "",
    val balance: String = "",
    val number: String = "",
)