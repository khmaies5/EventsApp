package com.khmaies.seatgeekevents.utils

import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Currency
import java.util.Locale

object AppUtils {

    const val ENDPOINT = "https://api.seatgeek.com/2/"

    fun convertUtcToLocalDateTime(utcDateTimeString: String): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
        val utcDateTime = LocalDateTime.parse(utcDateTimeString, formatter)

        val localDateTime =
            utcDateTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime()

        val displayFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        return localDateTime.format(displayFormatter)
    }

    fun formatPrice(price: Long?): String {
        return when {
            price == null || price == 0L -> "Free"
            else -> {
                val currencyInstance = NumberFormat.getCurrencyInstance(Locale.getDefault())
                currencyInstance.currency = Currency.getInstance(Locale.getDefault())
                currencyInstance.format(price)
            }
        }
    }

}