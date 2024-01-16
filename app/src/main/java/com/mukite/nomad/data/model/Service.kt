package com.mukite.nomad.data.model

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.AirplanemodeActive
import androidx.compose.material.icons.filled.Atm
import androidx.compose.material.icons.filled.Bathtub
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.KingBed
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.RoomService
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Tv
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

    data object QueenSizeBed: Service(
        R.string.queenSizeBed,
        Icons.Filled.KingBed,
    )

    data object TV: Service(
        R.string.tv,
        Icons.Filled.Tv
    )

    data object Bathtub: Service(
        R.string.bathtub,
        Icons.Filled.Bathtub
    )

    data object Strongbox: Service(
        R.string.strongbox,
        Icons.Filled.Lock
    )

    data object Kitchen: Service(
        R.string.kithen,
        Icons.Filled.RestaurantMenu
    )

    data object airConditioning: Service(
        R.string.air_conditioning,
        Icons.Filled.AcUnit
    )

    data object Pool: Service(
        R.string.pool,
        Icons.Filled.Pool
    )

    data object Parking: Service(
        R.string.parking,
        Icons.Filled.DirectionsCar
    )


}