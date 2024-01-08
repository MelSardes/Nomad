package com.mukite.nomad.ui.booking_details

import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mukite.nomad.R
import com.mukite.nomad.data.NomadUiState
import com.mukite.nomad.ui.NomadViewModel
import com.mukite.nomad.ui.RoomType
import com.mukite.nomad.utils.calculateDaysDifference
import com.mukite.nomad.utils.formatLongToDate
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


enum class ReservationState {
    RESERVATION_INFORMATION, PERSONAL_INFORMATION, SUMMARY
}

@Composable
fun BookingDetails(
    viewModel: NomadViewModel,
    modifier: Modifier = Modifier,
    onBack: () -> Unit
) {


    val uiState by viewModel.uiState.collectAsState()


//    val daysDifference = remember {
//        calculateDaysDifference(
//            uiState.selectedBookingDates.first!!,
//            uiState.selectedBookingDates.second!!
//        )
//    }


    var reservationState by rememberSaveable {
        mutableStateOf(ReservationState.RESERVATION_INFORMATION)
    }


    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier, verticalArrangement = Arrangement.spacedBy(16.dp)) {
            TopBar() {
                if (reservationState != ReservationState.RESERVATION_INFORMATION) {
                    reservationState = ReservationState.RESERVATION_INFORMATION
                } else {
                    onBack()
                }
            }

            when (reservationState) {
                ReservationState.RESERVATION_INFORMATION -> {
                    ReservationInformation(
                        uiState = uiState,
                        viewModel = viewModel,
                        Modifier
                            .animateContentSize(
                                animationSpec = spring(
                                    dampingRatio = 0.8f,
                                    stiffness = 500f
                                )
                            )
                            .fillMaxSize(),
                        onBackPressed = { onBack() },
                    )
                }

                else -> {
                    ReservationSummary(
                        uiState,
                        modifier
                            .animateContentSize(
                                animationSpec = spring(
                                    dampingRatio = 0.8f,
                                    stiffness = 500f
                                )
                            )
                            .fillMaxSize(),
                    ) {
                        reservationState = ReservationState.RESERVATION_INFORMATION
                    }
                }
            }
        }

        BottomAppBar(modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
        ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            text = "Prix total",
                            style = MaterialTheme.typography.labelMedium,
                        )
                        Text(
                            text = uiState.totalPrice.toString() + " Fcfa",
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        )
                    }
//                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        modifier = Modifier.fillMaxHeight(),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 44.dp),
                        onClick = { reservationState = ReservationState.SUMMARY }
                    ) {
                        Text(text = if (reservationState == ReservationState.RESERVATION_INFORMATION) "Suivant" else "Payer", style = MaterialTheme.typography.titleMedium)
                    }

                }
            }
        }

}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(title: Int = R.string.booking_details,  onBack: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = title),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReservationSummary(uiState: NomadUiState, modifier: Modifier, onBackPressed: () -> Unit) {
    val dates = Pair(
        formatLongToDate(uiState.selectedBookingDates.first),
        formatLongToDate(uiState.selectedBookingDates.second)
    )

    val today = remember {
        LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE dd/MM/yyyy").withLocale(Locale.FRANCE))
    }

    val daysDifference = calculateDaysDifference(
        uiState.selectedBookingDates.first,
        uiState.selectedBookingDates.second
    )

    var selectedPaymentMethod by remember {
        mutableStateOf(1)
    }

    BackHandler(true) {
        onBackPressed()
    }

    Column(
        modifier = modifier,
//            .height(IntrinsicSize.Max),
        horizontalAlignment = Alignment.Start
    ) {

        Column(modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface)
//            .border(
//                width = 1.dp,
//                shape = RoundedCornerShape(4.dp),
//                color = MaterialTheme.colorScheme.outlineVariant
//            )
            .padding(vertical = 32.dp, horizontal = 16.dp),
        ) {
            InfoSection(
                "Date de réservation",
                today,
                modifier = Modifier.fillMaxWidth()
            )


            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))

            InfoSection("Statut", "En attente", modifier = Modifier.fillMaxWidth())
            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))

            InfoSection(
                "Réservé au nom de:",
                uiState.personalInformation.fullName,
                modifier = Modifier.fillMaxWidth()
            )
            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(100.dp)
            ) {
                InfoSection(
                    "Date d'arrivée",
                    dates.first,
                    modifier = Modifier.weight(1f)
                )
                InfoSection(
                    "Date de départ",
                    dates.second,
                    modifier = Modifier.weight(1f)
                )
            }

            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))

            InfoSection(
                "Détails",
                "$daysDifference nuit(s), 1 ${uiState.selectedRoomType.name.lowercase()}",
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
/*

        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = stringResource(id = R.string.payment_method),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold
            )

            Card(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .clickable {
                        selectedPaymentMethod = 1
                    }
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = card1Color,
                    contentColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                Row(
                    modifier = Modifier.padding(end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = selectedRadio == 1,
                        onClick = {
                            selectedRadio = 1
                            viewModel.updateTotalPrice(
                                RoomType.SUITE,
                                daysDifference
                            )
                        }
                    )
                    Text(
                        text = "Suite",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Card(onClick = {}, modifier = Modifier.fillMaxWidth())  {
                Text(
                    text = "PayPal",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Card(onClick = {}, modifier = Modifier.fillMaxWidth())  {
                Text(
                    text = "Airtel Money",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            Card(onClick = {}, modifier = Modifier.fillMaxWidth())  {
                Text(
                    text = "Moov Money",
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.Medium
                    )
                )
            }

        }
*/

    }
}

@Composable
fun InfoSection(title: String, data: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(vertical = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.8f),
            style = MaterialTheme.typography.titleSmall,
        )

        Text(
            text = data,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReservationInformation(
    uiState: NomadUiState,
    viewModel: NomadViewModel,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
) {

    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = Instant.now().toEpochMilli(),
        initialSelectedEndDateMillis = Instant.now().plus(Duration.ofDays(1)).toEpochMilli()
    )

    val numberOfDays = calculateDaysDifference(
        dateRangePickerState.selectedStartDateMillis ?: uiState.selectedBookingDates.first,
        dateRangePickerState.selectedEndDateMillis ?: uiState.selectedBookingDates.second
    )

    BackHandler(true) {
        onBackPressed()
    }


    var showDateRangePicker by remember { mutableStateOf(false) }

    if (showDateRangePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDateRangePicker = false
                viewModel.updateTotalPrice(
                    roomType = uiState.selectedRoomType,
                    numberOfDays = numberOfDays
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    showDateRangePicker = !showDateRangePicker
                    viewModel.updateBookingDates(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis ?: uiState.selectedBookingDates.first,
                            dateRangePickerState.selectedEndDateMillis ?: uiState.selectedBookingDates.second
                        )
                    )
                    viewModel.updateTotalPrice(
                        roomType = uiState.selectedRoomType,
                        numberOfDays = numberOfDays
                    )

                }) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDateRangePicker = false
                    viewModel.updateTotalPrice(
                        roomType = uiState.selectedRoomType,
                        numberOfDays = numberOfDays
                    )
                }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
        ) {
            DateRangePicker(
                state = dateRangePickerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(500.dp)
            )
        }
    }

    Column(modifier = modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {

        DateSection(
            dateRangePickerState = dateRangePickerState,
            uiState = uiState,
            onClick = { showDateRangePicker = !showDateRangePicker },
        )

        Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 20.dp))

        RoomTypes(viewModel = viewModel, uiState = uiState)

        Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 20.dp))
        GuestsSection(uiState, viewModel)

        Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 20.dp))
        OutlinedTextField(
            value = uiState.particularDemand,
            onValueChange = { viewModel.updateParticularDemand(it) },
            label = { Text("Demande particulière") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        PersonalInformation(
            uiState = uiState,
            viewModel = viewModel,
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun DialogDatePicker() {

}

@Composable
private fun RoomTypes(viewModel: NomadViewModel, uiState: NomadUiState) {
    val daysDifference = calculateDaysDifference(
        uiState.selectedBookingDates.first,
        uiState.selectedBookingDates.second
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        var selectedRadio by rememberSaveable {
            mutableIntStateOf(1)
        }

        val card1Color = if (selectedRadio == 1) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        }

        val card2Color = if (selectedRadio == 2) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        }


        Card(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .clickable {
                    selectedRadio = 1
                    viewModel.updateTotalPrice(
                        RoomType.SUITE,
                        daysDifference
                    )
                }
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(8.dp)
                ),
            colors = CardDefaults.cardColors(
                containerColor = card1Color,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Row(
                modifier = Modifier.padding(end = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                RadioButton(
                    selected = selectedRadio == 1,
                    onClick = {
                        selectedRadio = 1
                        viewModel.updateTotalPrice(
                            RoomType.SUITE,
                            daysDifference
                        )
                    }
                )
                Text(
                    text = "Suite",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Card(
            modifier = Modifier
                .weight(1f)
                .width(IntrinsicSize.Max)
                .clickable {
                    selectedRadio = 2
                    viewModel.updateTotalPrice(
                        RoomType.APPARTEMENT,
                        daysDifference
                    )
                }
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = RoundedCornerShape(8.dp)
                ),
            colors = CardDefaults.cardColors(
                containerColor = card2Color,
                contentColor = MaterialTheme.colorScheme.onSurface
            )
        ) {
            Row(
                modifier = Modifier.padding(end = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                RadioButton(selected = selectedRadio == 2, onClick = {
                    selectedRadio = 2
                    viewModel.updateTotalPrice(
                        RoomType.APPARTEMENT,
                        daysDifference
                    )})
                Text(
                    text = "Appartement",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun PersonalInformation(
    uiState: NomadUiState,
    viewModel: NomadViewModel,
    modifier: Modifier = Modifier,
) {
    Text(
        text = stringResource(id = R.string.personalInformation),
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.SemiBold
    )

    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = uiState.personalInformation.firstName,
        onValueChange = { viewModel.updateFirstName(it) },
        label = { Text(stringResource(R.string.lastname)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = uiState.personalInformation.lastName,
        onValueChange = { viewModel.updateLastName(it) },
        label = { Text(stringResource(R.string.firstname)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
        modifier = Modifier.fillMaxWidth()

    )

    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = uiState.personalInformation.email,
        onValueChange = { viewModel.updateEmail(it) },
        label = { Text(stringResource(R.string.email)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = uiState.personalInformation.phoneNumber,
        onValueChange = { viewModel.updatePhoneNumber(it) },
        label = { Text(stringResource(R.string.phoneNumber)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = null) },
        modifier = Modifier.fillMaxWidth()
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSection(
    dateRangePickerState: DateRangePickerState,
    uiState: NomadUiState,
    onClick: () -> Unit,
    ) {

    val daysDifference = calculateDaysDifference(
        uiState.selectedBookingDates.first,
        uiState.selectedBookingDates.second
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            Modifier
                .padding(8.dp)
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onClick() }
                .padding(8.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dateRangePickerState.selectedStartDateMillis?.let { millis ->
                    formatLongToDate(millis)
                } ?: uiState.selectedBookingDates.first.toString(),
                maxLines = 2,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.check_in),
                style = MaterialTheme.typography.labelSmall,
            )
        }

        Text(
            text = daysDifference.run {
                if (this.toInt() == 1) {
                    "1 nuit"
                } else if (this > 1) {
                    "$this\nnuits"
                } else {
                    "En\nattente"
                }
            },
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .background(color = Color.DarkGray, shape = RoundedCornerShape(4.dp))
                .padding(vertical = 2.dp, horizontal = 10.dp)
        )


        Column(
            Modifier
                .padding(8.dp)
                .background(
                    MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable { onClick() }
                .padding(8.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = dateRangePickerState.selectedEndDateMillis?.let { millis ->
                    formatLongToDate(millis)
                } ?: uiState.selectedBookingDates.second.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp),
            )
            Text(
                text = stringResource(id = R.string.check_out),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun GuestsSection(
    uiState: NomadUiState,
    viewModel: NomadViewModel,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = R.string.persons),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = viewModel::decreaseNumberOfGuests) {
                Text(text = "-", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
            }

            Text(
                text = uiState.numberOfGuests.toString(),
                style = MaterialTheme.typography
                    .bodyLarge
                    .copy(color = MaterialTheme.colorScheme.inversePrimary)
            )

            TextButton(onClick = viewModel::increaseNumberOfGuests) {
                Text(text = "+", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
            }
        }

    }
}

@Preview(showBackground = true, device = "id:pixel_7", showSystemUi = true)
@Composable
fun BookingDetailsPreview() {
    BookingDetails(
        viewModel = NomadViewModel()
    ) {}
}