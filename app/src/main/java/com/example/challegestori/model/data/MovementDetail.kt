package com.example.challegestori.model.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class MovementDetail(
    val amount: String = "",
    val cuenta: String = "",
    val date: String = "",
    val description: String = "",
    val folio: String = "",
    val hour: String = "",
    val type: String = "",
) : Parcelable