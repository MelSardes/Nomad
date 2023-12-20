package com.mukite.nomad.data.datasource

import com.mukite.nomad.data.model.Service


object DataSource {
    val servicesDataSource = listOf(
        Service.AirportWelcome,
        Service.UnlimitedInternet,
        Service.Fitness,
        Service.RoomService,
    )
}