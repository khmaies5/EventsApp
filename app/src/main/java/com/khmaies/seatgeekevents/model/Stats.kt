package com.khmaies.seatgeekevents.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Stats(
    @SerializedName("average_price")
    val eventPrice: Long
): Serializable
