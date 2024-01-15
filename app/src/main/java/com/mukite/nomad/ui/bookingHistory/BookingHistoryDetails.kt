package com.mukite.nomad.ui.bookingHistory

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mukite.nomad.R
import com.mukite.nomad.data.model.BookingStatus
import com.mukite.nomad.ui.NomadViewModel
import com.mukite.nomad.ui.booking_details.InfoSection
import com.mukite.nomad.utils.calculateDaysDifference
import java.text.SimpleDateFormat
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
        modifier = modifier.verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        TopBar() { onBackPressed() }
        Column(modifier = Modifier
            .padding(8.dp)
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 24.dp, horizontal = 16.dp),
        ) {
            InfoSection(
                "Code de réservation",
                booking?.reservationCode ?: "Code non renseigné",
                modifier = Modifier.fillMaxWidth()
            )
            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))

            InfoSection(
                "Date de réservation",
                genericDateToLocalFrDateFormatter(booking?.date, true),
                modifier = Modifier.fillMaxWidth()
            )
            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))

            InfoSection(
                "Statut",
                when (booking?.status) {
                    BookingStatus.ONGOING -> "En cours"
                    BookingStatus.COMPLETED -> "Complet"
                    BookingStatus.CANCELED -> "Annulé"
                    else -> "Statut non renseigné"
                },
                modifier = Modifier.fillMaxWidth(),
                dataTextModifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        when (booking?.status) {
                            BookingStatus.ONGOING -> Color(0xFFA9D6E5)
                            BookingStatus.COMPLETED -> Color(0xFFB7E4C7)
                            BookingStatus.CANCELED -> MaterialTheme.colorScheme.errorContainer
                            else -> Color.Transparent
                        }
                    )
                    .padding(4.dp)
            )
            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))

            InfoSection(
                "Réservé au nom de:",
                "Morriss Morrisson",
                modifier = Modifier.fillMaxWidth()
            )
            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.Start),
            ) {
                InfoSection(
                    "Date d'arrivée",
                    genericDateToLocalFrDateFormatter(booking?.checkIn, true),
                    modifier = Modifier.weight(1f)
                )
                InfoSection(
                    "Date de départ",
                    genericDateToLocalFrDateFormatter(booking?.checkOut, true),
                    modifier = Modifier.weight(1f)
                )
            }

            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))

            InfoSection(
                "Détails",
                "$daysDifference nuit(s), 1 ${booking?.roomType}",
            )

            Divider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f))

            InfoSection(
                "Total",
                "${daysDifference * booking?.price!!}  Franc CFA",
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
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
            }
        }
    )
}
