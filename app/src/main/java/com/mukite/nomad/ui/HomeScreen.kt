package com.mukite.nomad.ui

import android.os.Build
import android.os.VibrationEffect
import android.system.Os
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mukite.nomad.R
import com.mukite.nomad.data.model.News
import com.mukite.nomad.data.model.PhotosGalleryType


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navigateToDateSelection: () -> Unit,
    onWeatherClick: () -> Unit,
    navigateToArticle: (news: News) -> Unit
) {
    val verticalScrollState = rememberScrollState()
    val dialogImageViewerOpened = remember { mutableStateOf(false) }
    var selectedImageType by remember {
        mutableStateOf(PhotosGalleryType.ESTABLISHMENT)
    }
    val haptic = LocalHapticFeedback.current

    AnimatedVisibility(visible = dialogImageViewerOpened.value, modifier = Modifier.padding(0.dp).fillMaxSize()) {
        DialogImageViewer(selectedImageType) {
            dialogImageViewerOpened.value = false
        }
    }

    Column(
        modifier = modifier
            .padding(0.dp)
            .verticalScroll(verticalScrollState)
            .fillMaxSize()
    ) {
        Box(modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()) {
            VideoPlayer(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
            )

            Image(
                painter = painterResource(id = R.drawable.logo_no_bg),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier.fillMaxSize(),
                alpha = 0.3f,
                alignment = Alignment.Center
            )
        }
//        Spacer(modifier = Modifier.height(14.dp))

        Column(
            modifier = Modifier
                .offset(y = (-12).dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background)
        ) {
            HeaderSection(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))

            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Color.Black.copy(alpha = 0.1f), modifier = Modifier, thickness = 8.dp)
            Spacer(modifier = Modifier.height(24.dp))

            NewsSection(modifier = Modifier.fillMaxWidth()) { navigateToArticle(it) }

            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Color.Black.copy(alpha = 0.1f), modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(24.dp))

            WeatherSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) { onWeatherClick() }

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(16.dp),
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    navigateToDateSelection()
                }
            ) {
                Text(
                    text = stringResource(R.string.booking_now),
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)) {
                Image(
                    painter = painterResource(id = R.drawable.airtel_ad_banner),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth
                )
            }


            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Color.Black.copy(alpha = 0.1f), modifier = Modifier, thickness = 8.dp)
            Spacer(modifier = Modifier.height(24.dp))
            DescriptionSection(modifier = Modifier.padding(horizontal = 16.dp))

            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Color.Black.copy(alpha = 0.1f), modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(24.dp))

            GallerySection(modifier = Modifier.fillMaxWidth()) {
                dialogImageViewerOpened.value = true
                selectedImageType = it
            }

            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Color.Black.copy(alpha = 0.1f), modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(24.dp))

            ServicesSection(modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Color.Black.copy(alpha = 0.1f), modifier = Modifier, thickness = 8.dp)
            Spacer(modifier = Modifier.height(24.dp))

            MapSection(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}

@Composable
fun TabItem(icon: ImageVector, index: Int, selectedIndex: Int, modifier: Modifier = Modifier, onTabSelected: (Int) -> Unit) {
    val selected = selectedIndex == index
    val tint = if (selected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = modifier
            .selectable(
                selected = selected,
                onClick = { onTabSelected(index) }
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = tint)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = "Tab $index", color = tint)
    }
}

@RequiresApi(Build.VERSION_CODES.Q)
@Preview(showBackground = true, showSystemUi = true, device = "id:small_phone")
@Composable
fun HomeScreenPreview() {
    HomeScreen(modifier = Modifier, {}, {}) {}
}
