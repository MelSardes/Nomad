package com.mukite.nomad.ui.bookingHistory

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
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
import com.mukite.nomad.utils.calculateDaysDifference
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingHistory(
    viewModel: NomadViewModel,
    modifier: Modifier = Modifier,
    navigateToDetails: () -> Unit
) {
    val haptic = LocalHapticFeedback.current

    val uiState = viewModel.uiState.collectAsState()
    var selectedChip by rememberSaveable { mutableIntStateOf(0) }
    val searchBarState = remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        AnimatedVisibility(visible = !(searchBarState.value)) {
            TopBar() { searchBarState.value = !searchBarState.value }
        }

        AnimatedVisibility(visible = searchBarState.value) {
            SearchBar(
                query = uiState.value.searchQuery,
                onQueryChange = {
                    viewModel.onSearchQueryChange(it)
                },
                onSearch = {},
                active = searchBarState.value,
                onActiveChange = {
                    searchBarState.value = it
                },
                placeholder = { Text(text = stringResource(id = R.string.search_placeholder), color = Color.LightGray) },
                leadingIcon = {
                    if (searchBarState.value) {
                        IconButton(onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                            searchBarState.value = false
                        }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = null,
                            )
                        }
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                        )
                    }
                },
                trailingIcon = {
                    if (uiState.value.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onSearchQueryChange("") }) {
                            Icon(
                                imageVector = Icons.Filled.Cancel,
                                contentDescription = null,
                            )
                        }
                    }
                },
                windowInsets = WindowInsets.captionBar
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                ) {
                    item {
                        Text(
                            text = "Dernieres réservations",
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    items(DataSource.bookingSampleList.shuffled().take(6), key = { it.id }) {
                        BookingCard(booking = it) {
                            viewModel.selectedBookingHistoryItem(it)
                            navigateToDetails()
                        }
                    }
                }
            }
        }

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
        ) {
            ChipsOptions(
                selectedChip = selectedChip,
                modifier = Modifier.fillMaxWidth()
            ) { selectedChip = it }

            BookingList(selectedChip) {
                viewModel.selectedBookingHistoryItem(it)
                navigateToDetails()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(onSearchIconTap: () -> Unit = {}) {
    val haptic = LocalHapticFeedback.current

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.reservations),
                style = MaterialTheme.typography.titleMedium
            )
        },
        actions = {
            IconButton(onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                onSearchIconTap()
            }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            }
        }
    )
}

@Composable
private fun BookingList(
    selectedChip: Int,
    onCardClick: (Booking) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
//        .verticalScroll(scrollState), verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item { Spacer(modifier = Modifier.height(24.dp)) }
        items(
            when (selectedChip) {
                1 -> DataSource.bookingSampleList.filter { it.status == BookingStatus.ONGOING }
                2 -> DataSource.bookingSampleList.filter { it.status == BookingStatus.COMPLETED }
                3 -> DataSource.bookingSampleList.filter { it.status == BookingStatus.CANCELED }
                else -> DataSource.bookingSampleList
            },
            key = { it.id }) {
            BookingCard(
                booking = it,
                modifier = Modifier.fillMaxWidth()
            ) { onCardClick(it) }
        }
    }
}


val chipsTags = listOf(
    Pair("En cours", Icons.Filled.Timelapse),
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

    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(
            alignment = Alignment.CenterHorizontally,
            space = 8.dp
        )
    ) {
        chipsTags.forEachIndexed { index, (chipTag, icon) ->
            val newIndex = index.plus(1)

            FilterChip(
                selected = selectedChip == newIndex,
                onClick = {
                    if (selectedChip == newIndex) onChipSelected(0)
                    else onChipSelected(newIndex)
                },
                leadingIcon = { Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                ) },
                label = {
                    Text(chipTag, style = MaterialTheme.typography.labelMedium)
                },
                colors = InputChipDefaults.inputChipColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    selectedContainerColor = when (newIndex) {
                        1 -> Color(0xFFA9D6E5)
                        2 -> Color(0xFFB7E4C7)
                        3 -> MaterialTheme.colorScheme.errorContainer
                        else -> MaterialTheme.colorScheme.primary
                    },
                    selectedLabelColor = when (newIndex) {
                        1 -> Color(0xFF013A63)
                        2 -> Color(0xFF1B4332)
                        3 -> MaterialTheme.colorScheme.onErrorContainer
                        else -> MaterialTheme.colorScheme.primary
                    }
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingCard(booking: Booking, modifier: Modifier = Modifier, onClick: () -> Unit) {
    val daysDifference = calculateDaysDifference(
        SimpleDateFormat("dd/MM/yyyy").parse(booking?.checkIn).time,
        SimpleDateFormat("dd/MM/yyyy").parse(booking?.checkOut).time
    )

    Card(
        onClick = { onClick() },
        modifier = modifier.height(IntrinsicSize.Max),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
//                .height(100.dp)
                .padding(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(50.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(
                        when (booking.status) {
                            BookingStatus.ONGOING -> Color(0xFFA9D6E5)
                            BookingStatus.COMPLETED -> Color(0xFFB7E4C7)
                            BookingStatus.CANCELED -> MaterialTheme.colorScheme.errorContainer
                        }
                    )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.Start),
                    verticalAlignment = Alignment.Top,
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "Réservé le",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        )

                        Text(
                            text = genericDateToLocalFrDateFormatter(booking.date),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "Durée",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        )
                        Text(
                            text = daysDifference.toString() + " nuit(s)",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "Arrivée",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        )
                        Text(
                            text = genericDateToLocalFrDateFormatter(booking.checkIn),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Column(Modifier.weight(1f)) {
                        Text(
                            text = "Depart",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        )
                        Text(
                            text = genericDateToLocalFrDateFormatter(booking.checkOut),
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "${daysDifference * booking?.price!!} Fcfa",
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                )
            }
        }
    }
}


internal fun genericDateToLocalFrDateFormatter(stringDate: String?, withWeekDay: Boolean = false): String {

    stringDate ?: return "N/A"

    // Parse the input string to a LocalDate object
    val localDate = LocalDate.parse(stringDate, DateTimeFormatter.ofPattern("dd/MM/yyyy"))

    // Format the LocalDate with the desired pattern including the day of the week
    val dateFormatter = DateTimeFormatter.ofPattern(if (withWeekDay) "EEEE\ndd MMMM yyyy" else "dd MMMM yyyy").withLocale(Locale.FRANCE)
    val formattedDate = localDate.format(dateFormatter)

    return formattedDate
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