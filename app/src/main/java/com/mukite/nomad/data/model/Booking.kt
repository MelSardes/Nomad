package com.mukite.nomad.data.model

data class Booking(
    val name: String,
    val date: String,
    val startDate: String,
    val endDate: String,
    val reservationCode: String
) {
    companion object {
        val ROOM_PRICE = 181_000

        val sample = listOf(
            Booking(
                name = "John",
                date = "2022-01-01",
                startDate = "2022-01-01",
                endDate = "2022-01-01",
                reservationCode = "ABC123"
            ),
            Booking(
                name = "Jane",
                date = "2022-01-02",
                startDate = "2022-01-02",
                endDate = "2022-01-02",
                reservationCode = "DEF456"
            ),
            Booking(
                name = "Joe",
                date = "2022-01-03",
                startDate = "2022-01-03",
                endDate = "2022-01-03",
                reservationCode = "GHI789"
            ),
            Booking(
                name = "Jack",
                date = "2022-01-04",
                startDate = "2022-01-04",
                endDate = "2022-01-04",
                reservationCode = "JKL012"
            )
        )
    }
}

