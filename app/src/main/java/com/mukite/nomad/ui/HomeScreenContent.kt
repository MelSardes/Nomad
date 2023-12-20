package com.mukite.nomad.ui

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mukite.nomad.R
import com.mukite.nomad.data.datasource.DataSource
import com.mukite.nomad.data.model.Service


@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = stringResource(R.string.nomad_hotel),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.SemiBold
        )

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.tertiary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                ) {
                    append(stringResource(id = R.string.price))
                }
                append("/")
                append(stringResource(id = R.string.night))
            }
        )
    }

    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        Image(
            imageVector = Icons.Rounded.LocationOn,
            contentDescription = null,
            modifier = Modifier.size(12.dp)
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
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.Start
    ) {
        HeaderSectionTitle(title = R.string.location)

        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier, contentAlignment = Alignment.Center) {
/*
            Image(
                painter = painterResource(id = R.drawable.nomad_main),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
*/
        }
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
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            maxItemsInEachRow = 4
        ) {
            DataSource.servicesDataSource.forEach { service ->
                ServiceItem(modifier = Modifier.weight(1f), service = service)
            }
        }
    }
}

@Composable
fun ServiceItem(modifier: Modifier = Modifier, service: Service) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center.also { Arrangement.spacedBy(4.dp) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = service.icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(id = service.name),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.SemiBold
        )

    }
}

@Composable
fun DescriptionSection(isContentHidden: Boolean = true, clickOnContent: () -> Unit) {

    Column {
        HeaderSectionTitle(title = R.string.description)
        Spacer(modifier = Modifier.height(8.dp))


        Text(
            modifier = Modifier
                .animateContentSize()
                .clickable(onClick = { clickOnContent() }),

            text = buildAnnotatedString {
                append(stringResource(R.string.nomad_hotel_description))

                withStyle(
                    style = SpanStyle(
                        textDecoration = TextDecoration.Underline,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                    )
                ) {
                    append(stringResource(id = R.string.see_more))
                }
            },
            maxLines = if (isContentHidden) 5 else Int.MAX_VALUE,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun GallerySection(modifier: Modifier = Modifier) {
    Column(horizontalAlignment = Alignment.Start, modifier = modifier) {
        HeaderSectionTitle(title = R.string.photo_gallery, link = R.string.see_all)

        Spacer(modifier = Modifier.height(8.dp))

/*
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.height(112.dp)
        ) {
            items(galleryImages) { image ->
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .width(132.dp)
                        .clip(RoundedCornerShape(12.dp)),
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
*/
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


/*
private val galleryImages = listOf<R.drawable>(
*/
/*    R.drawable.gallerie1,
    R.drawable.gallerie2,
    R.drawable.gallerie3,
    R.drawable.gallerie4,
    R.drawable.gallerie5,
    R.drawable.gallerie6,
    R.drawable.gallerie7,
    R.drawable.gallerie8,
    R.drawable.gallerie9,*//*

)
*/
