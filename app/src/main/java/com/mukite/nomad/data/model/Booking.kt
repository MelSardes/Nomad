package com.mukite.nomad.data.model

import java.time.LocalDate
import java.time.ZoneOffset

data class Booking(
    val id: Int,
    val hotel: String,
    val place: String,
    val status: BookingStatus,
    val price: Int,
    val date: String,
    val checkIn: String,
    val checkOut: String,
    val reservationCode: String,
    val roomType: String,

    ) {
    companion object {
        val ROOM_PRICE = 181_000
    }
        fun checkInToLongDate(): Long = LocalDate.parse(checkIn).atStartOfDay().toInstant(
            ZoneOffset.UTC).toEpochMilli()

        fun checkOutToLongDate(): Long = LocalDate.parse(checkOut).atStartOfDay().toInstant(
            ZoneOffset.UTC).toEpochMilli()

        fun dateToLongDate(): Long = LocalDate.parse(date).atStartOfDay().toInstant(
            ZoneOffset.UTC).toEpochMilli()

}

enum class BookingStatus {
    ONGOING, COMPLETED, CANCELED
}