package com.mukite.nomad.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatLongToDate(longValue: Long?): String =
    longValue?.let {
        Instant.ofEpochMilli(longValue)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .format(DateTimeFormatter.ofPattern("EEEE dd/MM/yyyy", Locale.getDefault()))
    } ?: "Error null date"

fun calculateDaysDifference(startTimestamp: Long, endTimestamp: Long): Long {
    val startDate = Instant.ofEpochMilli(startTimestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val endDate = Instant.ofEpochMilli(endTimestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    return endDate.toEpochDay() - startDate.toEpochDay()
}
