package com.khmaies.seatgeekevents.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    @SerializedName("title")
    val eventName: String,
    @SerializedName("datetime_utc")
    val eventDate: String,
    @SerializedName("venue")
    val eventVenue: Venue,
    @SerializedName("stats")
    val eventStats: Stats
) : Parcelable
