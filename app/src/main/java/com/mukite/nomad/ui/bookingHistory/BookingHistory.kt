package com.mukite.nomad.ui.bookingHistory

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mukite.nomad.R
import com.mukite.nomad.data.datasource.DataSource
import com.mukite.nomad.data.model.Booking
import com.mukite.nomad.data.model.BookingStatus
import com.mukite.nomad.ui.NomadViewModel

@Composable
fun BookingHistory(
    viewModel: NomadViewModel,
    modifier: Modifier = Modifier,
    navigateToDetails: () -> Unit
) {
    val scrollState = rememberScrollState()

    var selectedChip by rememberSaveable { mutableIntStateOf(0) }

    Column(modifier = modifier.padding(16.dp)) {
        TopBar()
        ChipsOptions(
            selectedChip = selectedChip,
            modifier = Modifier.width(IntrinsicSize.Max)
        ) { selectedChip = it }

        Spacer(modifier = Modifier.padding(16.dp))
        BookingList(selectedChip, scrollState)  {
            viewModel.selectedBookingHistoryItem(it)
            navigateToDetails()
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.reservations),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )
        },
        actions = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = null)
        }
    )
}

@Composable
private fun BookingList(selectedChip: Int, scrollState: ScrollState, onCardClick: (Booking) -> Unit) {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(scrollState), verticalArrangement = Arrangement.spacedBy(12.dp)) {

        when (selectedChip) {
            0 -> {
                DataSource.bookingSampleList.filter { it.status == BookingStatus.ONGOING }
                    .forEach { booking ->
                        BookingCard(
                            booking = booking,
                            modifier = Modifier.fillMaxWidth()
                        ) { onCardClick(booking) }
                    }
            }

            1 -> {
                DataSource.bookingSampleList.filter { it.status == BookingStatus.COMPLETED }
                    .forEach { booking ->
                        BookingCard(
                            booking = booking,
                            modifier = Modifier.fillMaxWidth()
                        ) { onCardClick(booking) }
                    }
            }

            2 -> {
                DataSource.bookingSampleList.filter { it.status == BookingStatus.CANCELED }
                    .forEach { booking ->
                        BookingCard(
                            booking = booking,
                            modifier = Modifier.fillMaxWidth()
                        ) { onCardClick(booking) }
                    }
            }
        }
    }
}

val chipsTags = listOf(
    Pair("En cours",Icons.Filled.Timelapse),
    Pair("Complet", Icons.Filled.Check),
    Pair("Annulé", Icons.Filled.Cancel),
)

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun ChipsOptions(
    selectedChip: Int,
    modifier: Modifier = Modifier,
    onChipSelected: (Int) -> Unit
) {

    Row(modifier = modifier, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        chipsTags.forEachIndexed { index, (chipTag, icon) ->
            FilterChip(
                selected = selectedChip == index,
                onClick = { onChipSelected(index) },
                leadingIcon = { Icon(imageVector = icon, contentDescription = null) },
                label = { Text(chipTag) },
                colors = InputChipDefaults.inputChipColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    selectedContainerColor = when (index) {
                        0 -> MaterialTheme.colorScheme.secondaryContainer
                        1 -> MaterialTheme.colorScheme.inverseSurface
                        2 -> MaterialTheme.colorScheme.errorContainer
                        else -> MaterialTheme.colorScheme.primary
                    },
                    selectedLabelColor = when (index) {
                        0 -> MaterialTheme.colorScheme.onSecondaryContainer
                        1 -> MaterialTheme.colorScheme.inverseOnSurface
                        2 -> MaterialTheme.colorScheme.onErrorContainer
                        else -> MaterialTheme.colorScheme.primary
                    },
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingCard(booking: Booking, modifier: Modifier = Modifier, onClick: () -> Unit) {

    Card(
        onClick = { onClick() },
        modifier = modifier.height(IntrinsicSize.Max),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(modifier = Modifier
            .fillMaxSize()
            .height(100.dp)
            .padding(8.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nomad_main),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 4.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "Arrivée",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        )
                        Text(
                            text = booking.checkIn,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "Depart",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        )
                        Text(
                            text = booking.checkOut,
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "181.000 Fcfa",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                )
            }
        }
    }
}


@Preview(name = "Booking Card")
@Composable
fun BookingCardPreview() {
    BookingCard(
        DataSource.bookingSampleList[0],
    ) { }
}

@Preview(name = "Booking", device = "id:pixel_7", showSystemUi = true)
@Composable
private fun PreviewBooking() {
    BookingHistory(NomadViewModel()) {}
}