package com.mukite.nomad.ui

import androidx.annotation.StringRes
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_OFF
import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.mukite.nomad.R
import com.mukite.nomad.data.datasource.DataSource
import com.mukite.nomad.data.datasource.DataSource.galleryImages
import com.mukite.nomad.data.datasource.DataSource.newsList
import com.mukite.nomad.data.datasource.DataSource.weatherIconsList
import com.mukite.nomad.data.model.News
import com.mukite.nomad.data.model.Service
import com.mukite.nomad.utils.MapScreen
import com.mukite.nomad.utils.generateNext4Days
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun HeaderSection(modifier: Modifier = Modifier) {
/*
    val context = LocalContext.current

    val mediaItem = MediaItem.Builder()
        .setUri("asset:///nomad_hotel")
        .build()

    val exoPlayer = remember(context, mediaItem) {
        ExoPlayer.Builder(context)
            .build()
            .also { exoPlayer ->
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
                exoPlayer.repeatMode = REPEAT_MODE_ONE
            }
    }

    DisposableEffect(
        AndroidView(factory = {
            PlayerView(context).apply {
                player = exoPlayer
            }
        })
    ) {
        onDispose { exoPlayer.release() }
    }
*/


    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.nomad_hotel_full_name),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                imageVector = Icons.Rounded.LocationOn,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 4.dp)
                    .size(12.dp)
            )

            Text(
                text = stringResource(R.string.hotel_location),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Start,
                modifier = Modifier
            )
        }
    }
}

@androidx.annotation.OptIn(UnstableApi::class) @Composable
fun VideoPlayer(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val videoPath = "android.resource://${context.packageName}/${R.raw.nomad_hotel}"
    // Create a SimpleExoPlayer
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(videoPath.toUri()))
            prepare()
            play()
            volume = 0f
            repeatMode = REPEAT_MODE_ONE
        }
    }

    // Create a PlayerView for ExoPlayer
    val playerView = remember {
        PlayerView(context).apply {
            player = exoPlayer
            resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM
            useController = false
        }
    }

    // Use AndroidView to integrate PlayerView into Compose
    AndroidView(
        factory = { playerView },
        modifier = modifier
    ) { view ->
        // Do nothing here, AndroidView handles the view creation
    }

    // Remember to release the ExoPlayer when the Composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }
}

@Composable
fun MapSection(modifier: Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        HeaderSectionTitle(title = R.string.location)

        Spacer(modifier = Modifier.height(8.dp))
//        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
        MapScreen(modifier = Modifier
            .weight(1f)
            .height(IntrinsicSize.Max))
//        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ServicesSection(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        HeaderSectionTitle(title = R.string.services, modifier = Modifier.padding(horizontal = 16.dp))
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            DataSource.servicesDataSource.forEach { service ->
                ServiceItem(
                    modifier = Modifier,
                    service = service
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
        }
    }
}

@Composable
fun ServiceItem(modifier: Modifier = Modifier, service: Service) {
    Surface(
        modifier = modifier.size(72.dp),
        tonalElevation = 0.1.dp,
        shape = RoundedCornerShape(4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = service.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(id = service.name),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2,
                softWrap = true,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun DescriptionSection(modifier: Modifier = Modifier) {
    var isContentHidden by rememberSaveable { mutableStateOf(true) }

    Column(modifier  = modifier) {
        HeaderSectionTitle(title = R.string.description)
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .animateContentSize()
                .clickable(onClick = { isContentHidden = !isContentHidden }),

            text = buildAnnotatedString {
                append(stringResource(
                    if(isContentHidden) R.string.nomad_hotel_description_short
                    else R.string.nomad_hotel_description_expanded
                ))


                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                    ) {
                        append(stringResource(
                            id = if(isContentHidden) R.string.see_more else R.string.see_less
                        ))
                    }

            },
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun GallerySection(modifier: Modifier = Modifier, onImageClick: (image: Int) -> Unit) {
    Column(horizontalAlignment = Alignment.Start, modifier = modifier) {
        HeaderSectionTitle(
            title = R.string.photo_gallery,
            link = R.string.see_all,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.height(112.dp)
        ) {

            item {
                Spacer(modifier = Modifier.width(16.dp))
            }
            items(galleryImages) { image ->
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(132.dp)
                        .offset(x = ((-20)*galleryImages.indexOf(image)).dp)
                        .shadow(6.dp, RoundedCornerShape(12.dp), spotColor = Color.Black)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onImageClick(galleryImages.indexOf(image)) },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = image),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesPager(initialPage: Int, onClose: () -> Unit) {
    val state = rememberPagerState(initialPage = initialPage) { galleryImages.size }
    Column(modifier = Modifier
        .height(400.dp)
        .fillMaxSize(), horizontalAlignment = Alignment.End) {
        IconButton(
            onClick = { onClose() },
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = Color.White
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        HorizontalPager(state = state, modifier = Modifier.weight(1f)) { image ->
            Image(
                painter = painterResource(id = galleryImages[image]),
                contentDescription = null,
                modifier = Modifier,
                contentScale = ContentScale.FillWidth
            )
        }
    }
}

@Composable
fun DialogImageViewer(initialPage: Int, onClose: () -> Unit) {
    Dialog(onDismissRequest = { /*TODO*/ }, properties = DialogProperties(true, true)) {
        ImagesPager(initialPage = initialPage, onClose = onClose)
    }
}

@Composable
fun HeaderSectionTitle(@StringRes title: Int, modifier: Modifier = Modifier, @StringRes link: Int? = null) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = title),
//            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )

        link?.let {
            Text(
                text = stringResource(id = it),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}

@Composable
fun NewsSection(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.width(4.dp))
        newsList.forEach {
            NewsItem(news = it, modifier = Modifier)
        }
        Spacer(modifier = Modifier.width(4.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsItem(news: News, modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier.size(280.dp, 100.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        onClick = {},
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(green = 0.97f))
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier
                .fillMaxHeight()
                .width(100.dp)) {
                Image(
                    painter = painterResource(id = news.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
//            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = news.title).lowercase().replaceFirstChar { it.uppercase() },
//                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodyMedium ,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = stringResource(id = news.date),
//                color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun WeatherSection(modifier: Modifier = Modifier) {
    val fourNextDays = generateNext4Days()
    Surface(modifier = modifier, tonalElevation = 4.dp, shape = RoundedCornerShape(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            fourNextDays.forEach {
                DayItem(day = it)
            }
        }
    }
}

@Composable
fun DayItem(day: LocalDate, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = day.format(DateTimeFormatter.ofPattern("EEE").withLocale(Locale.FRANCE)),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = day.format(DateTimeFormatter.ofPattern("d MMM").withLocale(Locale.FRANCE)),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.labelLarge,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Image(
            painter = painterResource(id = weatherIconsList.random()),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = "${(18..38).random()}°",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.SemiBold
        )
    }
}

