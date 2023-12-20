package com.mukite.nomad.data.model

data class NomadUiState(
    val selectedBookingDates: Pair<Long?, Long?> = Pair(null, null),
    val numberOfGuests: Int = 1,
    val particularDemand: String = "",
    val showBottomBar: Boolean = true,
    val personalInformation: PersonalInformation = PersonalInformation(),
)

data class PersonalInformation(
    val lastName: String = "",
    val firstName: String = "",
    val email: String = "",
    val phoneNumber: String = ""
) {
    val fullName = "$firstName $lastName"
}