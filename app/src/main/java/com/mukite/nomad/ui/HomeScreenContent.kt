package com.mukite.nomad.ui

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import androidx.compose.ui.unit.times
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player.REPEAT_MODE_ONE
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.mukite.nomad.R
import com.mukite.nomad.data.datasource.DataSource
import com.mukite.nomad.data.datasource.DataSource.newsList
import com.mukite.nomad.data.datasource.DataSource.weatherIconsList
import com.mukite.nomad.data.datasource.photosGalleryDataSource.cateringServicePhotosGallery
import com.mukite.nomad.data.datasource.photosGalleryDataSource.establishmentPhotosGallery
import com.mukite.nomad.data.datasource.photosGalleryDataSource.eventsPhotosGallery
import com.mukite.nomad.data.datasource.photosGalleryDataSource.newsPhotosGallery
import com.mukite.nomad.data.model.News
import com.mukite.nomad.data.model.PhotosGalleryType
import com.mukite.nomad.data.model.Service
import com.mukite.nomad.utils.MapScreen
import com.mukite.nomad.utils.bounceClick
import com.mukite.nomad.utils.generateNext4Days
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun HeaderSection(modifier: Modifier = Modifier) {

    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.nomad_hotel_full_name),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                imageVector = Icons.Rounded.LocationOn,
                contentDescription = null,
                modifier = Modifier
                    .size(8.dp)
            )

            Text(
                text = stringResource(R.string.hotel_location),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }
    }
}

@androidx.annotation.OptIn(UnstableApi::class)
@Composable
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
fun ServiceItem(modifier: Modifier = Modifier, service: Service, horizontal: Boolean = false) {
    if (horizontal) {
        Surface(
            modifier = modifier
                .height(72.dp)
                .width(IntrinsicSize.Max),
            tonalElevation = 0.1.dp,
            shape = RoundedCornerShape(4.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
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
                    softWrap = false,
                    modifier = Modifier,
                    textAlign = TextAlign.Center
                )
            }
        }

    } else {
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
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun GallerySection(modifier: Modifier = Modifier, onImageClick: (type: PhotosGalleryType) -> Unit) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier.fillMaxWidth()
    ) {
        HeaderSectionTitle(
            title = R.string.photo_gallery,
            modifier = Modifier.padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.height(12.dp))
        PhotosGalleryRow { onImageClick(it) }

    }
}


@Composable
fun PhotoGalleryBox(
    photosGallery: List<Int>,
    galleryType: PhotosGalleryType,
    modifier: Modifier = Modifier,
    onImageClick: (PhotosGalleryType) -> Unit
) {

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier.bounceClick {
            onImageClick(galleryType)
        },
    ) {
        photosGallery.take(3).forEachIndexed { index, image ->
            val leftPadding = index * 20.dp
            val boxWidth = (132 - (index * 10)).dp
            val ratio = 132f / 112f
            val imageHeight = (boxWidth * ratio)

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(start = leftPadding)
                    .height(imageHeight)
                    .width(boxWidth)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(4.dp), spotColor = Color.Black)
                    .clip(RoundedCornerShape(4.dp))
                    .zIndex((-index).toFloat()),
            ) {
                Image(
                    painter = painterResource(id = image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Text(
            text = galleryType.value,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .padding(8.dp)
                .clip(RoundedCornerShape(25))
                .background(MaterialTheme.colorScheme.surface)
                .padding(4.dp)
                .align(Alignment.BottomStart)
        )
    }
}

@Composable
fun PhotosGalleryRow(onImageClick: (PhotosGalleryType) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier)

        PhotoGalleryBox(establishmentPhotosGallery, PhotosGalleryType.ESTABLISHMENT) { onImageClick(it) }

        PhotoGalleryBox(cateringServicePhotosGallery, PhotosGalleryType.CATERING_SERVICE) { onImageClick(it) }

        PhotoGalleryBox(eventsPhotosGallery, PhotosGalleryType.EVENTS) { onImageClick(it) }

        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Preview(showSystemUi = false, showBackground = true)
@Composable
fun GallerySectionPreview() {
    GallerySection(modifier = Modifier, onImageClick = { })
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImagesPager(imageType: PhotosGalleryType, modifier: Modifier = Modifier, onClose: () -> Unit) {

    val galleryImages = when (imageType) {
        PhotosGalleryType.ESTABLISHMENT -> establishmentPhotosGallery
        PhotosGalleryType.CATERING_SERVICE -> cateringServicePhotosGallery
        PhotosGalleryType.EVENTS -> eventsPhotosGallery
        PhotosGalleryType.NEWS -> newsPhotosGallery
    }
    val scope = rememberCoroutineScope()
    val state = rememberPagerState(initialPage = 0) { galleryImages.size }

    Column(
        modifier = modifier,
//        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        HorizontalPager(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            contentPadding = PaddingValues(0.dp),
        ) { image ->
            Image(
                painter = painterResource(id = galleryImages[image]),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Inside,
                alignment = Alignment.Center
            )
        }


        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = imageType.value,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
//                modifier = Modifier.weight(1f)
            )

            Text(
                "${state.currentPage + 1}/${galleryImages.size}",
                color = Color.White,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterHorizontally)
            ) {

                ElevatedButton(
                    enabled = state.currentPage != 0,
                    onClick = {
                        scope.launch {
                            state.animateScrollToPage(
                                state.currentPage - 1,
                                pageOffsetFraction = 0f,
                                animationSpec = spring(stiffness = 900f, dampingRatio = 0.9f),
                            )
                        }
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
    //                        tint = Color.White,
                    )
                }


                ElevatedButton(
                    enabled = state.currentPage != galleryImages.size - 1,
    //                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer, contentColor = MaterialTheme.colorScheme.onTertiaryContainer),
                    onClick = {
                        scope.launch {
                            state.animateScrollToPage(
                                state.currentPage + 1,
                                pageOffsetFraction = 0f,
                                animationSpec = spring(stiffness = 900f, dampingRatio = 0.9f),
                            )
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                    )
                }
            }

            FilledIconButton(
                onClick = {
                    onClose()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}


@Composable
fun DialogImageViewer(imageType: PhotosGalleryType, onClose: () -> Unit) {
    Dialog(onDismissRequest = { /*TODO*/ }, properties = DialogProperties(true, true)) {
        ImagesPager(imageType = imageType, modifier = Modifier.fillMaxSize()) { onClose() }
    }
}

@Composable
fun HeaderSectionTitle(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    @StringRes link: Int? = null,
    onClickLink: () -> Unit = {}
) {
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
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable { onClickLink() }
            )
        }
    }
}

@Composable
fun NewsSection(modifier: Modifier = Modifier, navigateToArticle: (news: News) -> Unit) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.width(4.dp))
        newsList.forEach {
            NewsItem(news = it, modifier = Modifier) { navigateToArticle(it) }
        }
        Spacer(modifier = Modifier.width(4.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsItem(news: News, modifier: Modifier = Modifier, onClick: () -> Unit) {
    ElevatedCard(
        modifier = modifier.size(280.dp, 100.dp).border(.1.dp, MaterialTheme.colorScheme.outline, shape = RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(0.1.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = { onClick() },
//        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(green = 0.97f))
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
                    style = MaterialTheme.typography.titleSmall ,
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
fun WeatherSection(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val fourNextDays = generateNext4Days()

    Surface(modifier = modifier.clickable { onClick() }, tonalElevation = 4.dp, shape = RoundedCornerShape(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)
        ) {
            fourNextDays.forEach { todayDate ->
                DayItem(
                    day = todayDate.format(DateTimeFormatter.ofPattern("EEEE").withLocale(Locale.FRANCE)),
                    date = todayDate.format(DateTimeFormatter.ofPattern("d MMM").withLocale(Locale.FRANCE)),
                    icon = weatherIconsList.random(),
                    temperature = "${(18..38).random()}°"
                )
            }
        }
    }
}

@Composable
fun DayItem(day: String, date: String, icon: Int, temperature: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = day,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = date,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.labelSmall,
        )
        Spacer(modifier = Modifier.height(12.dp))
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = temperature,
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.SemiBold
        )
    }
}

