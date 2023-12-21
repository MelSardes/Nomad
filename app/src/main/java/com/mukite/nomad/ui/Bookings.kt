package com.mukite.nomad.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mukite.nomad.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bookings(
    modifier: Modifier = Modifier
) {
    Column {
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

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            AssistChip(onClick = { /*TODO*/ }, label = { Text(text = "En cours") }, modifier = Modifier.padding(horizontal = 6.dp))
            AssistChip(onClick = { /*TODO*/ }, label = { Text(text = "Complets") }, modifier = Modifier.padding(horizontal = 6.dp))
            AssistChip(onClick = { /*TODO*/ }, label = { Text(text = "Annulés") }, modifier = Modifier.padding(horizontal = 6.dp))
        }


        BookingCard()

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingCard() {
    Card(onClick = { /*TODO*/ }, modifier = Modifier.padding(8.dp)) {
        Column {
            Row {
                Box(modifier = Modifier.size(80.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.nomad_main),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column {
                    Text(
                        text = stringResource(id = R.string.profile),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Place,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "Libreville",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }

                    Text(
                        text = "Complet",
                        style = MaterialTheme.typography.labelSmall,
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.tertiaryContainer)
                            .padding(4.dp)
                    )

                }
            }
        }
    }
}

@Preview(name = "Booking", device = "id:pixel_7", showSystemUi = true)
@Composable
private fun PreviewBooking() {
    Bookings()
}