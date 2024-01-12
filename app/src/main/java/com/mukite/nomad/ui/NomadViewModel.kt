package com.mukite.nomad.ui

import androidx.lifecycle.ViewModel
import com.mukite.nomad.data.NomadUiState
import com.mukite.nomad.data.model.Booking
import com.mukite.nomad.data.model.News
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class RoomType(val price: Int) {
    SUITE(281000), APPARTEMENT(181000)
}


class NomadViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NomadUiState())
    val uiState: StateFlow<NomadUiState> = _uiState.asStateFlow()


    fun updateTotalPrice(roomType: RoomType, numberOfDays: Long) {
        _uiState.update {
            it.copy(
                totalPrice = calculateTotalPrice(numberOfDays, roomType)
            )
        }
    }

    private fun calculateTotalPrice(numberOfDays: Long, roomType: RoomType): Long {
        return numberOfDays * roomType.price
    }

    fun updateUiState(newUiState: NomadUiState) {
        _uiState.value = newUiState
    }

    fun resetUiState() {
        _uiState.value = NomadUiState()
    }

    fun increaseNumberOfGuests() {
        _uiState.update {
            it.copy(
                numberOfGuests = if (it.numberOfGuests == 1) it.numberOfGuests + 1 else 2
            )
        }
    }

    fun decreaseNumberOfGuests() {
        _uiState.update {
            it.copy(
                numberOfGuests = if (it.numberOfGuests == 2) it.numberOfGuests - 1 else 1
            )
        }
    }

    fun updateBookingDates(selectedDates: Pair<Long, Long>) {
        _uiState.update {
            it.copy(selectedBookingDates = selectedDates)
        }
    }

    fun updateParticularDemand(demand: String) {
        _uiState.update { currentValue ->
            currentValue.copy(particularDemand = demand)
        }
    }

    fun updateFirstName(firstName: String) {
        _uiState.update { currentValue ->
            currentValue.copy(personalInformation = currentValue.personalInformation.copy(firstName = firstName))
        }
    }

    fun updateLastName(lastName: String) {
        _uiState.update { currentValue ->
            currentValue.copy(personalInformation = currentValue.personalInformation.copy(lastName = lastName))
        }
    }

    fun updateEmail(email: String) {
        _uiState.update { currentValue ->
            currentValue.copy(personalInformation = currentValue.personalInformation.copy(email = email))
        }
    }

    fun updatePhoneNumber(phoneNumber: String) {
        _uiState.update { currentValue ->
            currentValue.copy(personalInformation = currentValue.personalInformation.copy(phoneNumber = phoneNumber))
        }
    }

    fun updateShowBottomBar(showBottomBar: Boolean) {
        _uiState.update { currentValue ->
            currentValue.copy(showBottomBar = showBottomBar)
        }
    }

    fun selectedBookingHistoryItem(booking: Booking) {
        _uiState.update { currentValue ->
            currentValue.copy(selectedBookingHistoryItem = booking)
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { currentValue ->
            currentValue.copy(searchQuery = query)
        }
    }

    fun updateCurrentPageName(name: String) {
        _uiState.update { currentValue ->
            currentValue.copy(currentPageName = name)
        }
    }

    fun updateSelectedArticle(article: News) {
        _uiState.update { currentValue ->
            currentValue.copy(selectedArticle = article)
        }
    }

}