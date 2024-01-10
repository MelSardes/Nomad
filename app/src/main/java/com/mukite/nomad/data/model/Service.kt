package com.mukite.nomad.data.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.Atm
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.RoomService
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.ui.graphics.vector.ImageVector
import com.mukite.nomad.R

sealed class Service(@StringRes val name: Int, val icon: ImageVector) {
    data object AirportWelcome : Service(
        R.string.airportWelcome,
        Icons.Filled.AirplanemodeActive
//        R.drawable.rounded_airplanemode_active_24
    )

    data object UnlimitedInternet: Service(
        R.string.unlimited_internet,
        Icons.Filled.Wifi
    )

    data object Fitness: Service(
        R.string.fitness,
        Icons.Filled.FitnessCenter
    )

    data object RoomService: Service(
        R.string.room_service,
        Icons.Filled.RoomService
    )

    data object Massage: Service(
        R.string.massage,
        Icons.Filled.Spa
    )

    data object ATM: Service(
        R.string.atm,
        Icons.Filled.Atm
    )

    data object ClothesService: Service(
        R.string.clothesService,
        Icons.Filled.Checkroom
    )
}