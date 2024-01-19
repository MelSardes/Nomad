package com.mukite.nomad.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.mukite.nomad.R
import com.mukite.nomad.data.datasource.DataSource
import com.mukite.nomad.data.datasource.DataSource.suitesPhotos

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Suites(modifier: Modifier = Modifier, navigateToDateSelection: () -> Unit) {
    val pagerState = rememberPagerState(0) {
        suitesPhotos.size
    }

    val haptic = LocalHapticFeedback.current

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.height(280.dp)) {

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    )
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Image(
                        painter = painterResource(id = suitesPhotos[it]),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            PagerIndicator(
                pagerState = pagerState,
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.our_suites),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                // render pdf document




                Divider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    modifier = Modifier.fillMaxWidth(.2f)
                )
                Divider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    modifier = Modifier.fillMaxWidth(.5f)
                )
                Divider(
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.inversePrimary,
                    modifier = Modifier.fillMaxWidth(.2f)
                )
            }

            Amenities()

            Column(modifier  = modifier) {
                HeaderSectionTitle(title = R.string.description)
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(id = R.string.suites_description),
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Justify
                )
            }

        }

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

        Spacer(modifier = Modifier.height(44.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)) {
            Image(
                painter = painterResource(id = R.drawable.disney_plus_banner_ad_example),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier = Modifier.height(44.dp))

    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Amenities(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        HeaderSectionTitle(
            title = R.string.equipment_and_services,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(8.dp))
        FlowRow (
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp),
//            maxItemsInEachRow = 5
        ) {
            DataSource.equipment_and_services.forEach { service ->
                ServiceItem(
                    modifier = Modifier.size(100.dp, 72.dp),
                    service = service,
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerIndicator(pagerState: PagerState, modifier: Modifier = Modifier) {
    val width = 8.dp
    Row(modifier = modifier) {
        repeat(suitesPhotos.size) { index ->
            val color = if (pagerState.currentPage == index) {
                MaterialTheme.colorScheme.primary
            } else {
                Color.White
            }

            AnimatedContent(
                targetState = width,
                label = "Animated Pager Indicator"
            ) { targetWidth ->
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .height(8.dp)
                        .width(if (pagerState.currentPage == index) 3.times(targetWidth) else targetWidth)
                        .clip(CircleShape)
                        .background(color = color)
                )
            }
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SuitesPreview() {
    Suites() {}
}
