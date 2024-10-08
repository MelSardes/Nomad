package com.mukite.nomad.data

import com.mukite.nomad.data.model.Booking
import com.mukite.nomad.data.model.News
import com.mukite.nomad.ui.RoomType
import java.time.Duration
import java.time.Instant


data class NomadUiState(
    val selectedBookingDates: Pair<Long, Long> = Pair(
        Instant.now().toEpochMilli(),
        Instant.now().plus(Duration.ofDays(1)).toEpochMilli()
    ),
    val numberOfGuests: Int = 1,
    val particularDemand: String = "",
    val showBottomBar: Boolean = true,
    val personalInformation: PersonalInformation = PersonalInformation(),
    val totalPrice: Long = 0,
    val selectedRoomType: RoomType = RoomType.SUITE,
    val selectedBookingHistoryItem: Booking? = null,
    val searchQuery: String = "",

    val currentPageName: String = "",
    val selectedArticle: News? = null
)

data class PersonalInformation(
    val lastName: String = "",
    val firstName: String = "",
    val email: String = "",
    val phoneNumber: String = ""
) {
    val fullName = "$firstName $lastName"
}