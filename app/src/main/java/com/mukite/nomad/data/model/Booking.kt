package com.mukite.nomad.data.model

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
}

enum class BookingStatus {
    ONGOING, COMPLETED, CANCELED
}