package com.mukite.nomad.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mukite.nomad.R

sealed class Service(@StringRes val name: Int, @DrawableRes val icon: Int) {
    data object AirportWelcome : Service(
        R.string.airportWelcome,
        R.drawable.rounded_airplanemode_active_24
    )

    data object UnlimitedInternet: Service(
        R.string.unlimited_internet,
        R.drawable.wifi
    )

    data object Fitness: Service(
        R.string.fitness,
        R.drawable.fitness
    )

    data object RoomService: Service(
        R.string.room_service,
        R.drawable.room_service
    )

    // TODO: CONTINUE LISTING SERVICES
}