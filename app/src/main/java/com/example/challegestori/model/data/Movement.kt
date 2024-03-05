package com.example.challegestori.model.data

import com.google.firebase.firestore.DocumentId

data class Movement(
    @DocumentId val id: String = "",
    val date: String = "",
    val description: String = "",
    val amount: String = "",
    val detail: MovementDetail = MovementDetail()
)