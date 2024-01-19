package com.mukite.nomad.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mukite.nomad.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDateScreen(
    viewModel: NomadViewModel,
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    onClickNext: () -> Unit,
) {
    val dateRangePickerState = rememberDateRangePickerState(initialDisplayMode = DisplayMode.Picker)
    val haptic = LocalHapticFeedback.current

    Box(modifier = modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
            // Add a row with "Save" and dismiss actions.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onClose() }) {
                    Icon(Icons.Filled.Close, contentDescription = "Localized description")
                }

                TextButton(
                    onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                        viewModel.updateBookingDates(
                            Pair(
                                dateRangePickerState.selectedStartDateMillis!!,
                                dateRangePickerState.selectedEndDateMillis!!
                            )
                        )

                        onClickNext()
                    },
                    enabled = dateRangePickerState.selectedEndDateMillis != null
                ) {
                    Text(text = "Suivant")
                }
            }

            DateRangePicker(
                dateFormatter = DatePickerFormatter(selectedDateSkeleton = "dd MMM yyyy"),
                state = dateRangePickerState,
                modifier = Modifier.weight(1f),
                title = {
                    Text(
                        stringResource(R.string.select_dates),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showSystemUi = true, showBackground = true, device = "id:pixel_7_pro")
@Composable
fun SelectDateScreenPreview() {
    SelectDateScreen(viewModel = NomadViewModel(),Modifier, {}) {}
}
