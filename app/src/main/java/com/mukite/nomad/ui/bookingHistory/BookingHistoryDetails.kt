package com.mukite.nomad.ui.bookingHistory

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mukite.nomad.R
import com.mukite.nomad.data.NomadUiState
import com.mukite.nomad.data.model.Booking
import com.mukite.nomad.ui.NomadViewModel
import com.mukite.nomad.ui.booking_details.InfoSection
import com.mukite.nomad.utils.calculateDaysDifference
import com.mukite.nomad.utils.formatLongToDate
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun BookingHistoryDetails(viewModel: NomadViewModel, modifier: Modifier, onBackPressed: () -> Unit) {
    val uiState by viewModel.uiState.collectAsState()
    val booking = uiState.selectedBookingHistoryItem

    val daysDifference = calculateDaysDifference(
        SimpleDateFormat("dd/MM/yyyy").parse(booking?.checkIn).time,
        SimpleDateFormat("dd/MM/yyyy").parse(booking?.checkOut).time
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
        TopBar() { onBackPressed() }
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
                booking?.date ?: "Date non renseignée",
                modifier = Modifier.fillMaxWidth()
            )


            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))

            InfoSection("Statut", "En attente", modifier = Modifier.fillMaxWidth())
            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))

            InfoSection(
                "Réservé au nom de:",
                "Morriss Morrisson",
                modifier = Modifier.fillMaxWidth()
            )
            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(100.dp)
            ) {
                InfoSection(
                    "Date d'arrivée",
                    booking?.checkIn ?: "Date non renseignée",
                    modifier = Modifier.weight(1f)
                )
                InfoSection(
                    "Date de départ",
                    booking?.checkOut ?: "Date non renseignée",
                    modifier = Modifier.weight(1f)
                )
            }

            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))

            InfoSection(
                "Détails",
                "$daysDifference nuit(s), 1 ${booking?.roomType}",
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(title: Int = R.string.booking_details, onBack: () -> Unit) {
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
