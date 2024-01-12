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
        Service.Massage,
        Service.ATM,
        Service.ClothesService,
    )

    val bookingSampleList = listOf(
        Booking(
            id = 1,
            hotel = "Nomad",
            place = "Libreville",
            status = BookingStatus.ONGOING,
            price = Booking.ROOM_PRICE,
            date = "10/05/2022",
            checkIn = "30/05/2022",
            checkOut = "03/06/2022",
            reservationCode = "#F7t5R5",
            roomType = "Suite",
        ),
        Booking(
            id = 2,
            hotel = "Nomad",
            place = "Libreville",
            status = BookingStatus.COMPLETED,
            price = Booking.ROOM_PRICE,
            date = "10/10/2022",
            checkIn = "10/10/2022",
            checkOut = "11/10/2022",
            reservationCode = "#F7t5R5",
            roomType = "Appartement",
        ),
        Booking(
            id = 3,
            hotel = "Nomad",
            place = "Libreville",
            status = BookingStatus.CANCELED,
            price = Booking.ROOM_PRICE,
            date = "10/10/2022",
            checkIn = "10/10/2022",
            checkOut = "11/10/2022",
            reservationCode = "#F7t5R5",
            roomType = "Appartement",
        ),
        Booking(
            id = 4,
            hotel = "Nomad",
            place = "Libreville",
            status = BookingStatus.ONGOING,
            price = Booking.ROOM_PRICE,
            date = "10/10/2022",
            checkIn = "10/10/2022",
            checkOut = "11/10/2022",
            reservationCode = "#F7d8P2",
            roomType = "suite",
        ),
        Booking(
            id = 5,
            hotel = "Nomad",
            place = "Libreville",
            status = BookingStatus.COMPLETED,
            price = Booking.ROOM_PRICE,
            date = "10/10/2022",
            checkIn = "10/10/2022",
            checkOut = "11/10/2022",
            reservationCode = "#F7d8P2",
            roomType = "Suite",
        ),
        Booking(
            id = 6,
            hotel = "Nomad",
            place = "Libreville",
            status = BookingStatus.ONGOING,
            price = Booking.ROOM_PRICE,
            date = "10/10/2022",
            checkIn = "10/10/2022",
            checkOut = "11/10/2022",
            reservationCode = "#F7d8P2",
            roomType = "Appartement",
        ),
        Booking(
            id = 7,
            hotel = "Nomad",
            place = "Libreville",
            status = BookingStatus.CANCELED,
            price = Booking.ROOM_PRICE,
            date = "10/10/2022",
            checkIn = "10/10/2022",
            checkOut = "11/10/2022",
            reservationCode = "#F7d8P2",
            roomType = "Suite",
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


    val galleryImages = listOf(
        R.drawable.gallerie1,
        R.drawable.gallerie2,
        R.drawable.gallerie3,
        R.drawable.gallerie4,
        R.drawable.gallerie5,
        R.drawable.gallerie6,
        R.drawable.gallerie7,
        R.drawable.gallerie8,
        R.drawable.gallerie9,
    )


    val weatherIconsList = listOf(
        R.drawable.rain,
        R.drawable.rain2,
        R.drawable.rain3,
        R.drawable.sun,
        R.drawable.sun_cloud,
        R.drawable.sun_rain,
        R.drawable.sun_rain2,
        R.drawable.thunderstorm,
        R.drawable.thunderstorm_rain,
    )
}

