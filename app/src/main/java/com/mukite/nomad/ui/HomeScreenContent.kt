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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mukite.nomad.R
import com.mukite.nomad.data.datasource.DataSource
import com.mukite.nomad.data.datasource.DataSource.newsList
import com.mukite.nomad.data.model.News
import com.mukite.nomad.data.model.Service
import com.mukite.nomad.utils.MapScreen
import com.mukite.nomad.utils.generateNext4Days
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun HeaderSection() {

    Text(
        text = stringResource(R.string.nomad_hotel_full_name),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.headlineMedium,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.fillMaxWidth()
    )

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        Image(
            imageVector = Icons.Rounded.LocationOn,
            contentDescription = null,
            modifier = Modifier
                .padding(end = 4.dp)
                .size(12.dp)
        )

        Text(
            text = "Quartier de l'aéroport, B.P. 8580, Libreville - Gabon",
            style = MaterialTheme.typography.labelSmall
        )
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
    Column {
        HeaderSectionTitle(title = R.string.services)
        Spacer(modifier = Modifier.height(8.dp))

        FlowRow(
            modifier = modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            verticalArrangement = Arrangement.spacedBy(30.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            maxItemsInEachRow = 4
        ) {
            DataSource.servicesDataSource.forEach { service ->
                ServiceItem(
                    modifier = Modifier,
                    service = service
                )
            }
        }
    }
}

@Composable
fun ServiceItem(modifier: Modifier = Modifier, service: Service) {
    Column(
        modifier = modifier
            .size(80.dp)
            .shadow(
                2.dp,
                spotColor = MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(8.dp)
            )
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp))
            .padding(8.dp)
        ,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            painter = painterResource(id = service.icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = stringResource(id = service.name),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            textAlign = TextAlign.Center,
            softWrap = true,
            modifier = Modifier
        )
    }
}

@Composable
fun DescriptionSection() {
    var isContentHidden by rememberSaveable { mutableStateOf(true) }

    Column {
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

                if(isContentHidden) {
                    withStyle(
                        style = SpanStyle(
                            textDecoration = TextDecoration.Underline,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                        )
                    ) {
                        append(stringResource(id = R.string.see_more))
                    }
                }
            },
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun GallerySection(modifier: Modifier = Modifier, onImageClick: (image: Int) -> Unit) {
    Column(horizontalAlignment = Alignment.Start, modifier = modifier) {
        HeaderSectionTitle(title = R.string.photo_gallery, link = R.string.see_all)

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(112.dp)
        ) {
            items(galleryImages) { image ->
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(132.dp)
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
fun HeaderSectionTitle(@StringRes title: Int, @StringRes link: Int? = null) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = title),
            color = MaterialTheme.colorScheme.secondary,
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
fun NewsSectionPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        NewsSection(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
fun NewsSection(modifier: Modifier = Modifier) {

    Row(modifier = modifier.horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        newsList.forEach {
            NewsItem(news = it, modifier = Modifier.width(280.dp))
        }
    }
}

@Composable
fun NewsItem(news: News, modifier: Modifier = Modifier) {
    Surface(modifier = modifier.height(90.dp), tonalElevation = 2.dp, shape = RoundedCornerShape(8.dp)) {
        Row {
            Box(modifier = Modifier.fillMaxHeight().width(100.dp)) {
                Image(
                    painter = painterResource(id = news.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp, 0.dp, 0.dp, 8.dp))
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.fillMaxHeight().padding(vertical = 4.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = news.title).lowercase().replaceFirstChar { it.uppercase() },
                    color = MaterialTheme.colorScheme.secondary,
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

@Preview
@Composable
fun NewsItemPreview() {
    Surface {
        NewsItem(news = newsList[0])
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
        Image(painter = painterResource(id = R.drawable.sun), contentDescription = null, modifier = Modifier.size(32.dp))
        Text(
            text = "28°",
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.displaySmall,
            fontWeight = FontWeight.SemiBold
        )

    }
}


val galleryImages = listOf(
    R.drawable.gallerie1,
    R.drawable.gallerie2,
    R.drawable.gallerie3,
    R.drawable.gallerie4,
    R.drawable.gallerie5,
    R.drawable.gallerie6,
    R.drawable.gallerie7,
    R.drawable.gallerie8,
    R.drawable.gallerie9,
)
