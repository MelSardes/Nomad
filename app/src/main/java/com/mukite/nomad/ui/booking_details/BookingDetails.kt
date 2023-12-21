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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DateRangePickerState
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import com.mukite.nomad.data.model.NomadUiState
import com.mukite.nomad.ui.HeaderImage
import com.mukite.nomad.ui.HeaderSectionTitle
import com.mukite.nomad.ui.NomadViewModel
import com.mukite.nomad.utils.calculateDaysDifference
import com.mukite.nomad.utils.formatLongToDate
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.format.DateTimeFormatter


enum class ReservationState {
    RESERVATION_INFORMATION, PERSONAL_INFORMATION, SUMMARY
}

enum class DatePickerStateValue {
    START, END, NULL
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDetails(
    viewModel: NomadViewModel,
    modifier: Modifier = Modifier,
) {
    val updateDemand: (demand: String) -> Unit =
        remember(viewModel) { { demand -> viewModel.updateParticularDemand(demand) } }

    val updateFirstName: (firstName: String) -> Unit =
        remember(viewModel) { { firstName -> viewModel.updateFirstName(firstName) } }

    val updateLastName: (lastName: String) -> Unit =
        remember(viewModel) { { lastName -> viewModel.updateLastName(lastName) } }

    val updateEmail: (email: String) -> Unit =
        remember(viewModel) { { email -> viewModel.updateEmail(email) } }

    val updatePhoneNumber: (phoneNumber: String) -> Unit =
        remember(viewModel) { { phoneNumber -> viewModel.updatePhoneNumber(phoneNumber) } }

    val uiState by viewModel.uiState.collectAsState()

    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = Instant.now().toEpochMilli(),
        initialSelectedEndDateMillis = Instant.now().plus(Duration.ofDays(1)).toEpochMilli()
    )

//    val daysDifference = remember {
//        calculateDaysDifference(
//            uiState.selectedBookingDates.first!!,
//            uiState.selectedBookingDates.second!!
//        )
//    }

    var showDateRangePicker by remember { mutableStateOf(false) }

    var reservationState by rememberSaveable {
        mutableStateOf(ReservationState.RESERVATION_INFORMATION)
    }


    if (showDateRangePicker) {
        DatePickerDialog(
            onDismissRequest = {
                showDateRangePicker = false
            },
            confirmButton = {
                TextButton(onClick = {
                    showDateRangePicker = !showDateRangePicker
                    viewModel.updateBookingDates(
                        Pair(
                            dateRangePickerState.selectedStartDateMillis,
                            dateRangePickerState.selectedEndDateMillis
                        )
                    )
                }) {
                    Text(text = stringResource(id = R.string.confirm))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDateRangePicker = false
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


    Box(Modifier.fillMaxSize()) {
        HeaderImage(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        )

        Surface(
            modifier = modifier
                .animateContentSize(animationSpec = spring(dampingRatio = 0.8f, stiffness = 500f))
                .padding(horizontal = 24.dp)
                .height(IntrinsicSize.Min)
                .align(Alignment.Center),
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 4.dp
        ) {
            when (reservationState) {
                ReservationState.RESERVATION_INFORMATION -> {
                    ReservationInformation(
                        viewModel = viewModel,
                        dateRangePickerState,
                        Modifier.animateContentSize(
                            animationSpec = spring(
                                dampingRatio = 0.8f,
                                stiffness = 500f
                            )
                        ),
                        updateDemand,
                        onCheckInClick = { showDateRangePicker = !showDateRangePicker },
                        onCheckOutClick = { showDateRangePicker = !showDateRangePicker },
                    ) {
                        if (uiState.selectedBookingDates == Pair(null, null)) {
                            viewModel.updateBookingDates(
                                Pair(
                                    dateRangePickerState.selectedStartDateMillis,
                                    dateRangePickerState.selectedEndDateMillis
                                )
                            )
                        }
                        reservationState = ReservationState.PERSONAL_INFORMATION
                    }
                }

                ReservationState.PERSONAL_INFORMATION -> {
                    PersonalInformation(
                        uiState = uiState,
                        modifier = Modifier
                            .fillMaxWidth()
                            .animateContentSize(
                                animationSpec = spring(
                                    dampingRatio = 0.8f,
                                    stiffness = 500f
                                )
                            ),
                        updateFirstName = updateFirstName,
                        updateLastName = updateLastName,
                        updateEmail = updateEmail,
                        updatePhoneNumber = updatePhoneNumber,
                        onBackPressed = {
                            reservationState = ReservationState.RESERVATION_INFORMATION
                        },
                    ) { reservationState = ReservationState.SUMMARY }
                }

                else -> {
                    ReservationSummary(
                        uiState,
                        modifier.animateContentSize(
                            animationSpec = spring(
                                dampingRatio = 0.8f,
                                stiffness = 500f
                            )
                        ),
                    ) {
                        reservationState = ReservationState.PERSONAL_INFORMATION
                    }
                }
            }
        }
    }
}


@Composable
fun ReservationSummary(uiState: NomadUiState, modifier: Modifier, onBackPressed: () -> Unit) {
    val dates = Pair(
        formatLongToDate(uiState.selectedBookingDates.first),
        formatLongToDate(uiState.selectedBookingDates.second)
    )

    val today = remember {
        LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE dd/MM/yyyy"))
    }

    val daysDifference = calculateDaysDifference(
        uiState.selectedBookingDates.first!!,
        uiState.selectedBookingDates.second!!
    )


    BackHandler(true) {
        onBackPressed()
    }

    Column(
        modifier = modifier.padding(vertical = 32.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {

        InfoSection(
            "Date",
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
            horizontalArrangement = Arrangement.spacedBy(12.dp)
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
            "Prix total",
            "$daysDifference nuit, 1 chambre",
            modifier = Modifier.weight(1f)
        )

        Divider()
        Text(
            text = "181.000 Fcfa",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Procéder au paiment", style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Composable
private fun InfoSection(title: String, data: String, modifier: Modifier = Modifier) {
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
    viewModel: NomadViewModel,
    dateRangePickerState: DateRangePickerState,
    modifier: Modifier = Modifier,
    updateDemand: (String) -> Unit,
    onCheckInClick: () -> Unit,
    onCheckOutClick: () -> Unit,
    onNextStep: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsState()
    var selectedRadio by rememberSaveable {
        mutableIntStateOf(1)
    }

    val card1Color = if (selectedRadio == 1) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }
    val card1ContentColor = if (selectedRadio == 1) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    val card2Color = if (selectedRadio == 2) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }
    val card2ContentColor = if (selectedRadio == 2) {
        MaterialTheme.colorScheme.onPrimaryContainer
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Column(modifier = modifier.padding(16.dp)) {
        HeaderSectionTitle(title = R.string.nomad_hotel)

        Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 20.dp))
        DateSection(
            dateRangePickerState = dateRangePickerState,
            onCheckInClick = { onCheckInClick() },
            onCheckOutClick = { onCheckOutClick() }
        )

        Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .clickable { selectedRadio = 1 }
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = card1Color,
                    contentColor = card1ContentColor
                )
            ) {
                Row(
                    modifier = Modifier.padding(end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(selected = selectedRadio == 1, onClick = { selectedRadio = 1 })
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
                    .clickable { selectedRadio = 2 }
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(8.dp)
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = card2Color,
                    contentColor = card2ContentColor
                )
            ) {
                Row(
                    modifier = Modifier.padding(end = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(selected = selectedRadio == 2, onClick = { selectedRadio = 2 })
                    Text(
                        text = "Appartement",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 20.dp))
        GuestsSection(viewModel)

        Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 20.dp))
        OutlinedTextField(
            value = uiState.particularDemand,
            onValueChange = { updateDemand(it) },
            label = { Text("Demande particulière") },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { onNextStep() },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .align(Alignment.End),
        ) {
            Text(text = "Suivant")
        }
    }
}

@Composable
fun PersonalInformation(
    uiState: NomadUiState,
    modifier: Modifier = Modifier,
    updateFirstName: (String) -> Unit,
    updateLastName: (String) -> Unit,
    updateEmail: (String) -> Unit,
    updatePhoneNumber: (String) -> Unit,
    onBackPressed: () -> Unit,
    onSubmit: () -> Unit,
) {
    BackHandler(true) {
        onBackPressed()
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.personalInformation),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.personalInformation.firstName,
            onValueChange = { updateFirstName(it) },
            label = { Text(stringResource(R.string.lastname)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.personalInformation.lastName,
            onValueChange = { updateLastName(it) },
            label = { Text(stringResource(R.string.firstname)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Person, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()

        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.personalInformation.email,
            onValueChange = { updateEmail(it) },
            label = { Text(stringResource(R.string.email)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.personalInformation.phoneNumber,
            onValueChange = { updatePhoneNumber(it) },
            label = { Text(stringResource(R.string.phoneNumber)) },
            leadingIcon = { Icon(imageVector = Icons.Default.Phone, contentDescription = null) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                shape = RoundedCornerShape(8.dp),
                onClick = onBackPressed,
            ) {
                Text(text = "Précédent")
            }


            Button(
                onClick = { onSubmit() },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
            ) {
                Text(text = "Suivant", modifier = Modifier.padding(horizontal = 8.dp))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateSection(
    dateRangePickerState: DateRangePickerState,
    onCheckInClick: () -> Unit,
    onCheckOutClick: () -> Unit
) {

    val numberOfDays = remember {
        mutableIntStateOf(dateRangePickerState.selectedStartDateMillis?.let { startDate ->
            dateRangePickerState.selectedEndDateMillis?.let { endDate ->
                calculateDaysDifference(startDate, endDate)
            }
        }?.toInt() ?: -1)
    }

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
                .clickable { onCheckInClick() }
                .padding(8.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = dateRangePickerState.selectedStartDateMillis?.let { millis ->
                    formatLongToDate(millis)
                } ?: LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE\ndd/MM/yyyy")),
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
            text = numberOfDays.intValue.let {
                if (it == 1) {
                    "1 nuit"
                } else if (it > 1) {
                    "$it\nnuits"
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
                .clickable { onCheckOutClick() }
                .padding(8.dp)
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                text = dateRangePickerState.selectedEndDateMillis?.let { millis ->
                    formatLongToDate(millis)
                } ?: LocalDate.now().plusDays(1)
                    .format(DateTimeFormatter.ofPattern("EEEE\ndd/MM/yyyy")),
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
private fun GuestsSection(viewModel: NomadViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    val numberOfGuests by remember { derivedStateOf { uiState.numberOfGuests } }
    val decreaseNumberOfGuests = remember(viewModel) { { viewModel.decreaseNumberOfGuests() } }
    val increaseNumberOfGuests = remember(viewModel) { { viewModel.increaseNumberOfGuests() } }

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
            TextButton(onClick = decreaseNumberOfGuests) {
                Text(text = "-", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
            }

            Text(
                text = numberOfGuests.toString(),
                style = MaterialTheme.typography
                    .bodyLarge
                    .copy(color = MaterialTheme.colorScheme.inversePrimary)
            )

            TextButton(onClick = increaseNumberOfGuests) {
                Text(text = "+", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun BookingDetailsPreview() {
    BookingDetails(
        viewModel = NomadViewModel()
    )
}