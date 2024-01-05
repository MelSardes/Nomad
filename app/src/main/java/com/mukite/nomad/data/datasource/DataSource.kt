package com.mukite.nomad.data.datasource

import com.mukite.nomad.R
import com.mukite.nomad.data.model.Booking
import com.mukite.nomad.data.model.BookingStatus
import com.mukite.nomad.data.model.News
import com.mukite.nomad.data.model.Service


object DataSource {
    val servicesDataSource = listOf(
        Service.AirportWelcome,
        Service.UnlimitedInternet,
        Service.Fitness,
        Service.RoomService,
    )

    val bookingSampleList = listOf(
        Booking(
            hotel = "Nomad",
            place = "Libreville",
            status = BookingStatus.ONGOING,
            price = Booking.ROOM_PRICE,
            date = "10/05/2022",
            checkIn = "30/05/2022",
            checkOut = "03/06/2022",
            reservationCode = "#F7t5R5",
            roomType = "Standard",
        ),
        Booking(
            hotel = "Nomad",
            place = "Libreville",
            status = BookingStatus.COMPLETED,
            price = Booking.ROOM_PRICE,
            date = "10/10/2022",
            checkIn = "10/10/2022",
            checkOut = "11/10/2022",
            reservationCode = "#F7t5R5",
            roomType = "Standard",
        ),
        Booking(
            hotel = "Nomad",
            place = "Libreville",
            status = BookingStatus.CANCELED,
            price = Booking.ROOM_PRICE,
            date = "10/10/2022",
            checkIn = "10/10/2022",
            checkOut = "11/10/2022",
            reservationCode = "#F7t5R5",
            roomType = "Standard",
        ),
        Booking(
            hotel = "Nomad",
            place = "Libreville",
            status = BookingStatus.ONGOING,
            price = Booking.ROOM_PRICE,
            date = "10/10/2022",
            checkIn = "10/10/2022",
            checkOut = "11/10/2022",
            reservationCode = "#F7d8P2",
            roomType = "Standard",
        ),
        Booking(
            hotel = "Nomad",
            place = "Libreville",
            status = BookingStatus.COMPLETED,
            price = Booking.ROOM_PRICE,
            date = "10/10/2022",
            checkIn = "10/10/2022",
            checkOut = "11/10/2022",
            reservationCode = "#F7d8P2",
            roomType = "Standard",
        ),
        Booking(

            hotel = "Nomad",
            place = "Libreville",
            status = BookingStatus.ONGOING,
            price = Booking.ROOM_PRICE,
            date = "10/10/2022",
            checkIn = "10/10/2022",
            checkOut = "11/10/2022",
            reservationCode = "#F7d8P2",
            roomType = "Standard",
        ),
        Booking(
            hotel = "Nomad",
            place = "Libreville",
            status = BookingStatus.CANCELED,
            price = Booking.ROOM_PRICE,
            date = "10/10/2022",
            checkIn = "10/10/2022",
            checkOut = "11/10/2022",
            reservationCode = "#F7d8P2",
            roomType = "Standard",
        ),
    )

    val newsList = listOf(
        News(
            title = R.string.articleNews1_title,
            description = R.string.articleNews1_description,
            image = R.drawable.article_image1,
            date = R.string.articleNews1_date
        ),
        News(
            title = R.string.articleNews2_title,
            description = R.string.articleNews2_description,
            image = R.drawable.article_image2,
            date = R.string.articleNews2_date
        ),
        News(
            title = R.string.articleNews3_title,
            description = R.string.articleNews3_description,
            image = R.drawable.article_image3,
            date = R.string.articleNews3_date,
        )
    )
}

