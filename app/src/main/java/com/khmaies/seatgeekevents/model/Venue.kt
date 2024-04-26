package com.khmaies.seatgeekevents.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Venue(
    @SerializedName("extended_address")
    val address: String,
    @SerializedName("timezone")
    val timeZone: String
    //location
): Serializable
