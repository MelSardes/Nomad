package com.mukite.nomad.utils

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun formatLongToDate(longValue: Long?): String =
    longValue?.let {
        Instant.ofEpochMilli(longValue)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .format(DateTimeFormatter.ofPattern("EEEE dd/MM/yyyy", Locale.getDefault()))
    } ?: "Error null date"

fun calculateDaysDifference(startTimestamp: Long, endTimestamp: Long): Long {
    val startDate = Instant.ofEpochMilli(startTimestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    val endDate = Instant.ofEpochMilli(endTimestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    return endDate.toEpochDay() - startDate.toEpochDay()
}


fun generateNext4Days(): List<LocalDate> {
    val today = LocalDate.now()
    return (0 until 4).map { today.plusDays(it.toLong()) }
}

enum class ButtonState { Pressed, Idle }
fun Modifier.bounceClick(action: () -> Unit) = composed {
    var buttonState by remember { mutableStateOf(ButtonState.Idle) }
    val scale by animateFloatAsState(if (buttonState == ButtonState.Pressed) 0.9f else 1f)
    val haptic = LocalHapticFeedback.current

    this
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
        }
        .pointerInput(buttonState) {
            awaitPointerEventScope {
                buttonState = if (buttonState == ButtonState.Pressed) {
                    waitForUpOrCancellation()
                    ButtonState.Idle
                } else {
                    awaitFirstDown(false)
                    ButtonState.Pressed
                }
            }
        }
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null,
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                action()
            }
        )
}